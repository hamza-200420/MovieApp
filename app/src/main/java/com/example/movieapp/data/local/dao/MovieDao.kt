package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE movieId = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE movieId = :movieId)")
    suspend fun isMovieSaved(movieId: Int): Boolean

    @Query("SELECT * FROM movies ORDER BY addedAt DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>
}