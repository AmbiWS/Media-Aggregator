package ru.androidschool.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class Production(
    @SerializedName("name")
    var name: String = ""
)
