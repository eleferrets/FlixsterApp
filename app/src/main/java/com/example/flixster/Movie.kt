package com.example.flixster

import org.json.JSONArray

// Model for delegation from the main activity
data class Movie(
    val movieId: Int,
    // Devs don't care about the posterpath
    private val posterPath: String,
    private val backdropPath: String,
    val title: String,
    val overview: String,
) {
    // Devs care about the full url
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    val backdropImageUrl = "https://image.tmdb.org/t/p/w300/$backdropPath"

    companion object {
        // Call methods on the movie class without an instance of movie
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}