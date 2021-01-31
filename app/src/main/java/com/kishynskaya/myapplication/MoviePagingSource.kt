package com.kishynskaya.myapplication

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kishynskaya.myapplication.data.Movie

class MoviePagingSource : PagingSource<Int, Movie>() {

    override val keyReuseSupported: Boolean = false

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        // Start refresh at page 1 if undefined.
        return try {
            val nextPage = params.key ?: 1
            val response = MovieApiService.netService.getMovieList(nextPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1, // Only paging forward.
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    suspend fun search(search: String): LoadResult<Int, Movie> {
        return try {
            val response = MovieApiService.netService.searchMovie(search)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
