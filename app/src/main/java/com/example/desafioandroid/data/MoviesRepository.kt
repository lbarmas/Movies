package com.example.desafioandroid.data


import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.local.toLocalMovie
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalDataSource(private val dao: MoviesDao) {

    val movies: Flow<List<Movie>> = dao.getMovies().map { movies ->
        movies.map { it.toMovie() }
    }

    suspend fun updateMovie(movie: Movie) {
        dao.updateMovie(movie.toLocalMovie())
    }

    suspend fun insertAllMovies(movies: List<Movie>) {
        dao.insertAllMovies(movies.map { it.toLocalMovie() })
    }

    suspend fun countMovie(): Int {
        return dao.countMovie()
    }
}

class RemoteDataSource() {
    suspend fun getMovies(): List<Movie> {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            //gson factory
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesService::class.java)
            .getMovies()
            .results
            .map { it.toMovie() }
    }
}

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

