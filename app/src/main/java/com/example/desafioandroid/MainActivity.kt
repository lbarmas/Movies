package com.example.desafioandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.desafioandroid.data.local.MoviesDatabase
import com.example.desafioandroid.data.remote.ServerMovie
import com.example.desafioandroid.ui.screens.home.Home
import com.example.desafioandroid.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,  "movies-database"
        ).build()
        super.onCreate(savedInstanceState)
        setContent {
         Home(db.moviesDao())
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}