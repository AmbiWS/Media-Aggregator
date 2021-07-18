package ru.androidschool.intensiv

import androidx.fragment.app.Fragment
import dagger.Component
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment

@Component
interface ApplicationComponent {

    fun inject(fragment: MovieDetailsFragment)
}