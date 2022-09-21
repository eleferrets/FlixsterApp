package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


private const val TAG = "MovieAdapter"
const val MOVIE_EXTRA = "MOVIE_EXTRA"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Expensive operation: create a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    // Cheaper as you are binding to an existing viewholder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    // Implicit return
    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            var image = movie.posterImageUrl
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                image = movie.posterImageUrl
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                image = movie.backdropImageUrl
            }
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context).load(image).transform(CircleCrop()).placeholder(R.drawable.default_image).into(ivPoster)
        }

        override fun onClick(v: View?) {
            // Notify the particular movie clicked
            val movie = movies[adapterPosition]
            // Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()
            // Use intents to navigate to new activity
            val intent = Intent(context, DetailActivity::class.java)
            // intent.putExtra("movie")
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }
}
