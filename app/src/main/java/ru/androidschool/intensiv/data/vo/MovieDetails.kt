package ru.androidschool.intensiv.data.vo

import ru.androidschool.intensiv.data.dto.Genre
import ru.androidschool.intensiv.data.dto.Production

data class MovieDetails(
    var isFavoriteMovie: Boolean = false,
    var title: String = "",
    var votePoints: Double = 0.0,
    var aboutMovie: String? = "",
    var productionList: List<Production> = listOf(),
    var genre: List<Genre> = listOf(),
    var date: String = ""
) {
    val rating: Float
        get() = votePoints.div(2).toFloat()
}
