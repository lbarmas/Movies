package com.example.desafioandroid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.local.MoviesDao
import com.example.desafioandroid.data.local.toLocalMovie
import com.example.desafioandroid.data.local.toMovie
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.toLocalMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(private val dao: MoviesDao) : ViewModel() {
    private val _state = MutableStateFlow(UIState()) //MutableLiveData
    var state: StateFlow<UIState> = _state //LiveData<UIState>

    init {
        viewModelScope.launch {
            val isDbEmpty = dao.countMovie() == 0
            if (isDbEmpty) {
                _state.value = UIState(loading = true)
                delay(2000)
                dao.insertAllMovies(
                    Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        //gson factory
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(MoviesService::class.java)
                        .getMovies()
                        .results
                        .map { it.toLocalMovie() }
                )
            }
            _state.value = UIState(
                loading = false,
                movie = dao.getMovies().map { it.toMovie() })
        }
    }

    data class UIState(
        val loading: Boolean = false,
        val movie: List<Movie> = emptyList()
    )

     fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            dao.updateMovie(movie.copy(favorite = !movie.favorite).toLocalMovie())
        }
/*        val movies = _state.value.movie.toMutableList()
        movies.replaceAll {
            if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it
        }
        _state.value = _state.value.copy(movie = movies)*/
    }
}
