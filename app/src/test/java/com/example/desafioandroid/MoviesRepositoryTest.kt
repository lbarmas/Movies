package com.example.desafioandroid

import com.example.desafioandroid.data.MoviesRepository
import com.example.desafioandroid.data.local.LocalDataSource
import com.example.desafioandroid.data.remote.RemoteDataSource
import org.junit.Test
import org.mockito.Mockito.mock

class MoviesRepositoryTest {
    @Test
    fun `When Db is empty, server is called`() {
        val localDataSource = mock<LocalDataSource>()
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = MoviesRepository(localDataSource, remoteDataSource)
    }
}