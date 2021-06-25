package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("cast")
    var actorsList: List<Actor>? = listOf<Actor>()
)
