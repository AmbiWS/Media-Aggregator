package ru.androidschool.intensiv.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieContent(
    @ColumnInfo(name = "name")
    @SerializedName(value = "title", alternate = ["name"])
    var title: String = "",
    @Ignore
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @ColumnInfo(name = "poster")
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
