package com.jackz.kmmovies.repository

import com.jackz.kmmovies.Image
import com.jackz.kmmovies.MovieResponse
import com.jackz.kmmovies.remote.MovieService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MovieRepository {

    private val service = MovieService.create()

    private val scope = MainScope()

    fun getMovies(success: (MovieResponse) -> Unit){
        scope.launch {
            service.getMovies()?.let { success(it) }
        }
    }

    fun getImage(url: String, success: (Image?) -> Unit, failure: (Throwable?) -> Unit){
        scope.launch {
            service.getImage(url, success, failure)
        }
    }

}