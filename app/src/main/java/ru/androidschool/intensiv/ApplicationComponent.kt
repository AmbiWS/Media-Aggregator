package ru.androidschool.intensiv

import dagger.Component
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment
import ru.androidschool.intensiv.presentation.movie_details.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ViewModelModule::class))
interface ApplicationComponent {

    fun inject(fragment: MovieDetailsFragment)
}