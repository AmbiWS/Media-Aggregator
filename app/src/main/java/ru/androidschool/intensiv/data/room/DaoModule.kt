package ru.androidschool.intensiv.data.room

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.room.dao.MovieDao

@Module
class DaoModule {

    @Provides
    fun dao(application: Application): MovieDao? {
        return MovieDB.getInstance(application)?.movieDao()
    }
}
