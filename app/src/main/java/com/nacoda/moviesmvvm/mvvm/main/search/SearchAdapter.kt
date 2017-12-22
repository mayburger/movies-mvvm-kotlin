package com.nacoda.moviesmvvm.mvvm.main.search

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.nacoda.moviesmvvm.R
import com.nacoda.moviesmvvm.data.model.Movie
import com.nacoda.moviesmvvm.databinding.SearchMoviesItemBinding
import com.nacoda.moviesmvvm.mvvm.main.MainItemUserActionListener
import com.nacoda.moviesmvvm.util.helper.Network.IMAGE_URL
import com.nacoda.moviesmvvm.util.helper.getGenres

/**
 * Created by irfanirawansukirman on 04/12/17.
 */

class SearchAdapter(private var mMovies: List<Movie>?, private var mSearchViewModel: SearchViewModel, mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val mMainItemBinding: SearchMoviesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context),
                R.layout.search_movies_item, parent, false)

        return MainItemHolder(mMainItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val mMovieItem = mMovies!![position]

        val mUserActionListener = object : MainItemUserActionListener {
            override fun onMovieClicked(movie: Movie) {

            }
        }
        (holder as MainItemHolder).bindItem(mMovieItem, mUserActionListener)
    }

    override fun getItemCount(): Int {
        return mMovies!!.size
    }

    fun replaceData(mMovies: List<Movie>) {
        setList(mMovies)
    }

    fun setList(mMovies: List<Movie>) {
        this.mMovies = mMovies
        notifyDataSetChanged()
    }

    class MainItemHolder(itemView: SearchMoviesItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val mMainItemBinding: SearchMoviesItemBinding = itemView

        fun bindItem(movie: Movie, userActionListener: MainItemUserActionListener) {

            mMainItemBinding.item = movie
            mMainItemBinding.userActionListener = userActionListener
            mMainItemBinding.genres = getGenres(movie.genre_ids)
            try {
                mMainItemBinding.year = movie.release_date?.substring(0, 4)
            } catch (e:Exception){
                mMainItemBinding.year = "?"
            }
            mMainItemBinding.votes = movie.vote_count.toString()
            mMainItemBinding.posterPath = (IMAGE_URL + movie.poster_path)
            mMainItemBinding.rating = ((movie.vote_average.toFloat() * 10).toInt().toString() + "%")
            mMainItemBinding.executePendingBindings()


        }
    }


}