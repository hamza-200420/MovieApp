package com.example.movieapp.data.remote.datasource

import com.example.movieapp.data.remote.dto.UserDto
import com.example.movieapp.data.remote.mapper.toDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun signUp(name: String, email: String, password: String): UserDto {
        val firebaseUser = auth.createUserWithEmailAndPassword(email, password).await().user!!
        firebaseUser.updateProfile(userProfileChangeRequest { displayName = name }).await()
        return firebaseUser.toDto().copy(displayName = name)
    }

    suspend fun login(email: String, password: String): UserDto {
        return auth.signInWithEmailAndPassword(email, password).await().user!!.toDto()
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}