package com.kishynskaya.myapplication

import com.kishynskaya.myapplication.data.Movie
import com.kishynskaya.myapplication.data.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/popular")
    suspend fun getMovieList(@Query("number_page") pageNumber: Int = 1): MovieList

    @GET
    suspend fun getMovieInfo(@Query("movie_id") id: Int): Movie

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieList
}
