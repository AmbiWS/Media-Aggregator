package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieContent(
    @SerializedName(value = "title", alternate = ["name"])
    var title: String = "",
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("id")
    var id: Int = 0
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
