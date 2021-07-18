package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.domain.repository.IMovieCreditsRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers
import timber.log.Timber

class MovieCreditsUseCase(private val repository: IMovieCreditsRepository) {

    fun getMovieCredits(id: Int): Single<List<Actor>> {

        return repository.getMovieCredits(id)
            .doOnError { i -> Timber.e(i.toString()) }
            .applySchedulers()
    }
}