package com.jackz.kmmovies.remote

import com.jackz.kmmovies.Image
import com.jackz.kmmovies.MovieResponse
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


interface MovieService {

    suspend fun  getMovies() : MovieResponse?
    suspend fun getImage(url: String, success: (Image?) -> Unit, failure: (Throwable?) -> Unit)

    companion object {
        fun create(): MovieService {
            return MovieServiceImpl(
                client = HttpClient {
                    install(ContentNegotiation) {
                        json(Json{
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        })
                    }
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}