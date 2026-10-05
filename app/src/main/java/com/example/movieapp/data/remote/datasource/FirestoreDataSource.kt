package com.example.movieapp.data.remote.datasource

import com.example.movieapp.data.remote.dto.MovieFirestoreDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MovieFirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun favourites(
        uid: String = auth.currentUser?.uid ?: error("User not logged in")
    ): CollectionReference =
        firestore.collection("users").document(uid).collection("favourites")

    suspend fun insertMovie(movie: MovieFirestoreDto) {
        favourites()
            .document("${movie.mediaType}_${movie.movieId}")
            .set(movie.copy(addedAt = System.currentTimeMillis()))
            .await()
    }

    suspend fun deleteMovieById(movieId: Int, mediaType: String) {
        favourites().document("${mediaType}_$movieId").delete().await()
    }

    suspend fun isMovieSaved(movieId: Int, mediaType: String): Boolean {
        if (auth.currentUser == null) return false
        return favourites().document("${mediaType}_$movieId").get().await().exists()
    }

    fun getAllMovies(): Flow<List<MovieFirestoreDto>> {
        return callbackFlow {
            var registration: ListenerRegistration? = null
            val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                registration?.remove()
                val uid = firebaseAuth.currentUser?.uid
                if (uid == null) {
                    registration = null
                    trySend(emptyList())
                } else {
                    registration = favourites(uid)
                        .orderBy("addedAt", Query.Direction.DESCENDING)
                        .addSnapshotListener { snapshot, _ ->
                            if (snapshot != null) {
                                trySend(snapshot.toObjects(MovieFirestoreDto::class.java))
                            }
                        }
                }
            }

            auth.addAuthStateListener(authListener)
            awaitClose {
                auth.removeAuthStateListener(authListener)
                registration?.remove()
            }
        }
    }
}