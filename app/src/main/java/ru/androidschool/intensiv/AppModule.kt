package ru.androidschool.intensiv

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    fun app(): Application {
        return app
    }
}
