package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("name")
    var title: String? = "",
    @SerializedName("vote_average")
    var votePoints: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String? = ""
) {
    val rating: Float
        get() = votePoints.div(2).toFloat()
}
