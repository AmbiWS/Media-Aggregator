package ru.androidschool.intensiv

import android.app.Application
import androidx.room.Room
import ru.androidschool.intensiv.room.MovieDB
import timber.log.Timber

class MovieFinderApp : Application() {

    var db: MovieDB? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
    }

    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    fun getDatabase(): MovieDB {

        if (db == null)
            db = Room.databaseBuilder(
                applicationContext,
                MovieDB::class.java,
                "movies"
            ).build()

        return db as MovieDB
    }

    companion object {
        var instance: MovieFinderApp? = null
            private set

        const val LANGUAGE = "ru"
    }
}
