package com.jackz.kmmovies.remote

import com.jackz.kmmovies.Image
import com.jackz.kmmovies.MovieResponse
import com.jackz.kmmovies.toNativeImage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class MovieServiceImpl(private val client : HttpClient) : MovieService {


    override suspend fun getMovies() : MovieResponse? {
        try {
            val data = client.get<MovieResponse> {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HttpRoutes.baseUrl
                    encodedPath = "/trending/movie/week"
                    parameter("api_key", "")
                    parameter("page", "1")
                }
                headers {
                    append("Accept", "application/json")
                }

            }
            return data
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getImage(
        url: String,
        success: (Image?) -> Unit,
        failure: (Throwable?) -> Unit
    ) {
            try {
                HttpClient().get<ByteArray>(url)
                    .toNativeImage()
                    .also(success)
            } catch (ex: Exception) {
                failure(ex)
            }
    }
}