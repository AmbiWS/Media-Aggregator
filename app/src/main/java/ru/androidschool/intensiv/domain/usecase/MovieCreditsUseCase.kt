package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.errorCatching
import ru.androidschool.intensiv.domain.repository.IMovieCreditsRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers
import javax.inject.Inject

class MovieCreditsUseCase @Inject constructor(private val repository: IMovieCreditsRepository) {

    fun getMovieCredits(id: Int): Single<List<Actor>> {

        return repository.getMovieCredits(id)
            .errorCatching()
            .applySchedulers()
    }
}
