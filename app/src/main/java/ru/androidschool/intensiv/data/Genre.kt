package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("name")
    var name: String = ""
)