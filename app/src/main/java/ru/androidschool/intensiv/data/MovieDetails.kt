package ru.androidschool.intensiv.data

data class MovieDetails(
    var title: String? = "",
    var votePoints: Double = 0.0,
    var isFavoriteMovie: Boolean = false,
    var aboutMovie: String? = "",
    var actors: List<Actor>? = listOf<Actor>(),
    var production: String? = "",
    var genre: String? = "",
    var year: Int? = 0
) {
    val rating: Float
        get() = votePoints.div(2).toFloat()
}
