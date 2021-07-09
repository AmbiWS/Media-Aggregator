package ru.androidschool.intensiv.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDBEntity(
    @ColumnInfo(name = "name")
    var title: String = "",
    @ColumnInfo(name = "poster")
    var posterPath: String? = "",
    @PrimaryKey
    var id: Int = 0
) {
}