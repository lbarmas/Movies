package com.example.desafioandroid.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.remote.ServerMovie

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}

@Dao
interface MoviesDao {
    @Query("SELECT * FROM LocalMovie")
    suspend fun getMovies(): List<LocalMovie>
    @Insert
    suspend fun insertAllMovies(movies: List<LocalMovie>)
    @Update
    suspend fun updateMovie(movie: LocalMovie)
    @Query("SELECT COUNT(*) FROM LocalMovie")
    suspend fun countMovie(): Int
    fun getMovie(id: Int): LocalMovie
    fun deleteMovie(movie: LocalMovie)
}

@Entity
data class LocalMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean = false
)

fun LocalMovie.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    favorite = favorite
)