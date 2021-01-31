package com.kishynskaya.myapplication

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kishynskaya.myapplication.data.Movie

class ItemSearchPagingSource(private val search: String) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = MovieApiService.netService.searchMovie(search)
            LoadResult.Page(
                data = response.results,
                prevKey = null, // Only paging forward.
                nextKey = 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
