package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    var name: String = ""
)