package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    var isFavoriteMovie: Boolean = false,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("vote_average")
    var votePoints: Double = 0.0,
    @SerializedName("overview")
    var aboutMovie: String? = "",
    @SerializedName("production_companies")
    var productionList: List<Production> = listOf<Production>(),
    @SerializedName("genres")
    var genre: List<Genre> = listOf<Genre>(),
    @SerializedName("release_date")
    var date: String = ""
) {
    val rating: Float
        get() = votePoints.div(2).toFloat()
}
