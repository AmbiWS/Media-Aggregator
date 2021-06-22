package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("profile_path")
    var photoPath: String? = ""
    // https://www.themoviedb.org/t/p/w138_and_h175_face/
)
