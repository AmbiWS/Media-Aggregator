package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.MovieDetails
import ru.androidschool.intensiv.domain.repository.IMovieDetailsRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers
import timber.log.Timber

class MovieDetailsUseCase(private val repository: IMovieDetailsRepository) {

    fun getMovieDetails(id: Int): Single<MovieDetails> {

        return repository.getMovieDetails(id)
            .doOnError { i -> Timber.e(i.toString()) }
            .applySchedulers()
    }
}