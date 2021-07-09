package ru.androidschool.intensiv.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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
