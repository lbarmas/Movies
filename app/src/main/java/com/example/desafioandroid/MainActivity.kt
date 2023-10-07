package com.example.desafioandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.desafioandroid.data.LocalDataSource
import com.example.desafioandroid.data.MoviesRepository
import com.example.desafioandroid.data.RemoteDataSource
import com.example.desafioandroid.data.local.MoviesDatabase
import com.example.desafioandroid.ui.screens.home.Home
import com.example.desafioandroid.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    private lateinit var db : MoviesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,  "movies-database"
        ).build()

        val repository = MoviesRepository(
            localDataSource = LocalDataSource(db.moviesDao()),
            remoteDataSource = RemoteDataSource()
        )
        setContent {
         Home(repository)
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