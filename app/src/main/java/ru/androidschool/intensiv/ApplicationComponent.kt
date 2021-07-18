package ru.androidschool.intensiv

import dagger.Component
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment

@Component
interface ApplicationComponent {

    fun inject(fragment: MovieDetailsFragment)
}