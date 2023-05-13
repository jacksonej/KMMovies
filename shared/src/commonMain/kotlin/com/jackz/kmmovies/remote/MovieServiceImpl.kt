package com.jackz.kmmovies.remote

import com.jackz.kmmovies.Image
import com.jackz.kmmovies.MovieResponse
import com.jackz.kmmovies.toNativeImage
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*

class MovieServiceImpl(private val client : HttpClient) : MovieService {


    override suspend fun getMovies() : MovieResponse? {
        try {
            val data = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HttpRoutes.BASE_URL
                    path( "3/trending/movie/week")
                    parameter("api_key", "acbd932ef470b6bb6cbb0bd2aedac9a6")
                    parameter("page", "1")
                }
                headers {
                    append("Accept", "application/json")
                }

            }.body<MovieResponse>()
            return data
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


    override suspend fun getImage(
        url: String,
        success: (Image?) -> Unit,
        failure: (Throwable?) -> Unit
    ) {
            try {
                HttpClient().get(url).body<ByteArray>()
                    .toNativeImage()
                    .also(success)
            } catch (ex: Exception) {
                failure(ex)
            }
    }
}