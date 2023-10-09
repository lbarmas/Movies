package com.example.desafioandroid.data
import com.example.desafioandroid.data.local.LocalDataSource
import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.local.toLocalMovie
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.RemoteDataSource
import com.example.desafioandroid.data.remote.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    val movies: Flow<List<Movie>> = localDataSource.movies

    suspend fun updateMovie(movie: Movie) {
        localDataSource.updateMovie(movie)
    }

    suspend fun requestMovies() {
        val isDBEmpty = localDataSource.countMovie() == 0
        if (isDBEmpty) {
            localDataSource.insertAllMovies(remoteDataSource.getMovies())
        }
    }
}

