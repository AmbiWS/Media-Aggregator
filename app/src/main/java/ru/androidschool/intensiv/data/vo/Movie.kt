package ru.androidschool.intensiv.data.vo

data class Movie(
    var title: String = "",
    var voteAverage: Double = 0.0,
    var posterPath: String? = "",
    var id: Int = 0
)