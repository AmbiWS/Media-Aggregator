package ru.androidschool.intensiv.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.androidschool.intensiv.data.MovieContent

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieContent>

    @Insert
    fun insertAll(vararg movies: MovieContent)
}