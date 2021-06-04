package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie> {

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }

    fun getTvShows(): List<TvShow> {

        val tvShowsList = mutableListOf<TvShow>()
        for (x in 0..10) {
            val tvShow = TvShow(
                title = "QUEEN'S GAMBIT, SEASON " + (x + 1),
                votePoints = 10.0 - x
            )
            tvShowsList.add(tvShow)
        }

        return tvShowsList
    }

    fun getActors(): List<Actor> {

        val actorsList = mutableListOf<Actor>()
        for (x in 1..10) {
            val actor = Actor(
                name = "Anya Taylor-Joy"
            )
            actorsList.add(actor)
        }

        return actorsList
    }

    fun getDetails(): List<MovieDetails> {

    }
}
