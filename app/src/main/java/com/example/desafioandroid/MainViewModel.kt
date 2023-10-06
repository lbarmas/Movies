package com.example.desafioandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioandroid.data.remote.MoviesService
import com.example.desafioandroid.data.remote.ServerMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
        private val _state = MutableStateFlow(UIState()) //MutableLiveData
        var state: StateFlow<UIState> = _state //LiveData<UIState>
    init {
       viewModelScope.launch {
           _state.value = UIState(loading = true)
           delay(2000)
           _state.value = UIState(
               loading = false,
               movie = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                //gson factory
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesService::class.java)
                .getMovies()
                .results)
        }
    }
    data class UIState(
        val loading: Boolean = false,
        val movie: List<ServerMovie> = emptyList()
    )

    fun onMovieClick(movie: ServerMovie){
        val movies = _state.value.movie.toMutableList()
        movies.replaceAll{
            if(it.id == movie.id) movie.copy(favorite = !movie.favorite) else it }
            _state.value = _state.value.copy(movie = movies)
        }
    }
