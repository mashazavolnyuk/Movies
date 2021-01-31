package com.kishynskaya.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishynskaya.myapplication.data.Movie
import com.kishynskaya.myapplication.databinding.MovieItemBinding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator){

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            val data = getItem(position)
            val fullOath = "https://image.tmdb.org/t/p/w500" + data?.backdrop_path
            binding.title.text = getItem(position)!!.title
            binding.popularity.text = getItem(position)!!.popularity.toString()
            Glide
                .with(itemView)
                .load(fullOath)
                .into(binding.poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = MovieItemBinding.bind(view)
    }

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
