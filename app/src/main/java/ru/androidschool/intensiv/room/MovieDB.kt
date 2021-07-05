package ru.androidschool.intensiv.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.dao.MovieDao
import ru.androidschool.intensiv.data.MovieContent

@Database(entities = arrayOf(MovieContent::class), version = 1)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}