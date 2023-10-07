package com.example.desafioandroid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioandroid.data.Movie
import com.example.desafioandroid.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _state = MutableStateFlow(UIState()) //MutableLiveData
    var state: StateFlow<UIState> = _state //LiveData<UIState>

    init {
      viewModelScope.launch {
          _state.value = UIState(loading = true)
          repository.requestMovies()

          repository.movies.collect{
              _state.value = UIState(movie = it)
          }
      }
    }

    data class UIState(
        val loading: Boolean = false,
        val movie: List<Movie> = emptyList()
    )

     fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie.copy(favorite = !movie.favorite))
        }
/*        val movies = _state.value.movie.toMutableList()
        movies.replaceAll {
            if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it
        }
        _state.value = _state.value.copy(movie = movies)*/
    }
}
