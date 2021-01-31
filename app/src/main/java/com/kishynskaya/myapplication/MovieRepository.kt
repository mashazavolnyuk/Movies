package com.kishynskaya.myapplication

import com.kishynskaya.myapplication.data.MovieList

class MovieRepository {

    suspend fun loadMovieList(pageNumber: Int = 1) {
        MovieApiService.netService.getMovieList(pageNumber)
    }

    suspend fun loadMovieInfo(idMovie: Int) {
        MovieApiService.netService.getMovieInfo(idMovie)
    }

    suspend fun searchMovie(query: String): MovieList =
        MovieApiService.netService.searchMovie(query)

}
