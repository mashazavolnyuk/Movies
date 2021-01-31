package com.kishynskaya.myapplication

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.kishynskaya.myapplication.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListMovieModelView : ViewModel() {

    private val paging = MoviePagingSource()

    var movies: Flow<PagingData<Movie>> =
        Pager(PagingConfig(pageSize = 1000, initialLoadSize = 5)) {
            paging
        }.flow.cachedIn(viewModelScope)

    val queryMovies: MutableLiveData<PagingData<Movie>> = MutableLiveData()


    suspend fun search(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val value = paging.search(query)
//                Pager(
//                    config = PagingConfig(
//                        pageSize = 1000,
//                        enablePlaceholders = true
//                    ),
//                    pagingSourceFactory = { ItemSearchPagingSource(query) }
//                ).flow.cachedIn(viewModelScope).collectLatest {
//                    queryMovies.postValue(it)
//                }
            }
        }
    }


    fun loadMovieInfo(id: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    //  repository.loadMovieInfo(id)
                }
            } catch (e: Exception) {
                Log.d("ListMovieModelView", "loadMovie() " + e.localizedMessage)
            }

        }
    }
}
