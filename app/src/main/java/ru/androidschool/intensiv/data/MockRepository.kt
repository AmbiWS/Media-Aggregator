package ru.androidschool.intensiv.data

import ru.androidschool.intensiv.data.dto.MovieContent

object MockRepository {

    fun getMovies(): List<MovieContent> {

        val moviesList = mutableListOf<MovieContent>()
        for (x in 0..10) {
            val movie = MovieContent(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
