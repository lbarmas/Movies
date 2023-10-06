package com.example.desafioandroid.data.remote

import retrofit2.http.GET

interface MoviesService {
    @GET("discover/movie?api_key=d30e1f350220f9aad6c4110df385d380") //Peticiones al servicio
   suspend fun getMovies(): MovieResult //Peticiones en el mismo hilo
}