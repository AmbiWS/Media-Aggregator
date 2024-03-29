package ru.androidschool.intensiv

import dagger.Component
import ru.androidschool.intensiv.data.room.DaoModule
import ru.androidschool.intensiv.domain.usecase.DetailsUseCaseModule
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment
import ru.androidschool.intensiv.presentation.movie_details.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, AppModule::class, DaoModule::class, DetailsUseCaseModule::class])
interface ApplicationComponent {

    fun inject(fragment: MovieDetailsFragment)
}
