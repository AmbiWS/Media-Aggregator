package ru.androidschool.intensiv.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.androidschool.intensiv.dao.MovieDao
import ru.androidschool.intensiv.data.MovieContent

@Database(entities = arrayOf(MovieContent::class), version = 1)
abstract class MovieDB : RoomDatabase() {

    companion object {

        private var db: MovieDB? = null

        fun getInstance(context: Context): MovieDB? {

            if (db == null) {
                synchronized(MovieDB::class) {

                    db = Room.databaseBuilder(context.applicationContext,
                        MovieDB::class.java,
                        "movies-db")
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return db
        }
    }

    abstract fun movieDao(): MovieDao
}
