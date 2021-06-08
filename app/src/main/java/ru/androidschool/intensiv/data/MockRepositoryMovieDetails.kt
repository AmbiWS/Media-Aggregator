package ru.androidschool.intensiv.data

object MockRepositoryMovieDetails {

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

    fun getDetails(): MovieDetails {

        return MovieDetails(
            title = "Queen's Gambit",
            votePoints = 10.0,
            isFavoriteMovie = false,
            aboutMovie = "The Queen\'s Gambit follows the life of an orphan chess prodigy, Elizabeth Harmon, during her quest to become the world\'s greatest chess player while struggling with emotional problems, drugs and alcohol dependency. The title of the series refers to a chess opening of the same name.",
            actors = getActors(),
            production = "Flitcraft Ltd Wonderful Films",
            genre = "Drama",
            year = 2020
        )
    }

}