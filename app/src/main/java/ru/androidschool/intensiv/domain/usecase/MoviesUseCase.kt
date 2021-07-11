package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers
import timber.log.Timber

class MoviesUseCase(private val repository: MoviesRepository) {

    fun getMovies(): Single<List<Movie>> {

        return repository.getMovies()
            .doOnError { i -> Timber.d(i.toString()) }
            .applySchedulers()
    }
}