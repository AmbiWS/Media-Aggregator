package ru.androidschool.intensiv.data

class TvShow(
    var title: String? = "",
    var votePoints: Double = 0.0
) {
    val rating: Float
        get() = votePoints.div(2).toFloat()
}