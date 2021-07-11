package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("cast")
    var actorsList: List<Actor>? = listOf()
)
