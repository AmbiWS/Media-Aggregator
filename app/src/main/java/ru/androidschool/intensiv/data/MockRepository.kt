package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<MovieDBContent> {

        val moviesList = mutableListOf<MovieDBContent>()
        for (x in 0..10) {
            val movie = MovieDBContent(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
