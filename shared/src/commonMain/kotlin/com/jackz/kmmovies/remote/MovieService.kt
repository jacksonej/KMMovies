package com.jackz.kmmovies.remote

import com.jackz.kmmovies.Image
import com.jackz.kmmovies.MovieResponse
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

interface MovieService {

    suspend fun  getMovies() : MovieResponse?
    suspend fun getImage(url: String, success: (Image?) -> Unit, failure: (Throwable?) -> Unit)

    companion object {
        fun create(): MovieService {
            return MovieServiceImpl(
                client = HttpClient() {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            prettyPrint = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            )
        }
    }
}