package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title")
    var title: String = "",
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String? = ""
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
