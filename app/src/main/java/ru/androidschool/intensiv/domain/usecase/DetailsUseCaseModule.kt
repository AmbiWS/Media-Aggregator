package ru.androidschool.intensiv.domain.usecase

import dagger.Module
import dagger.Provides
import ru.androidschool.intensiv.data.repository.MovieCreditsRepository
import ru.androidschool.intensiv.data.repository.MovieDetailsRepository

@Module
class DetailsUseCaseModule {

    @Provides
    fun creditsUseCase(): MovieCreditsUseCase {
        return MovieCreditsUseCase(MovieCreditsRepository())
    }

    @Provides
    fun detailsUseCase(): MovieDetailsUseCase {
        return MovieDetailsUseCase(MovieDetailsRepository())
    }
}