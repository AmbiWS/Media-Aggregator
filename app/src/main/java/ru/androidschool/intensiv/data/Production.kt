package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class Production(
    @SerializedName("name")
    var name: String = ""
)