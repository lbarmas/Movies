package com.example.desafioandroid.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Query
import androidx.room.Update

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesDatabase {
    abstract fun moviesDao(): MoviesDao
}
@Dao
interface MoviesDao {
    @Query("SELECT * FROM LocalMovie")
    fun getMovies(): List<LocalMovie>
    @Update
    fun updateMovie(movie: LocalMovie)
    fun getMovie(id: Int): LocalMovie

    fun deleteMovie(movie: LocalMovie)
}
@Entity
data class LocalMovie(
    val id: Int,
    val overview: String,
    val title: String,
    val poster_path: String,
    val favorite: Boolean = false
)