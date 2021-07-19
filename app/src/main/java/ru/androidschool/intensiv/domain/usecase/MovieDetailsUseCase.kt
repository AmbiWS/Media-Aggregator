package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.MovieDetails
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.errorCatching
import ru.androidschool.intensiv.domain.repository.IMovieDetailsRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers
import javax.inject.Inject

class MovieDetailsUseCase (private val repository: IMovieDetailsRepository) {

    fun getMovieDetails(id: Int): Single<MovieDetails> {

        return repository.getMovieDetails(id)
            .errorCatching()
            .applySchedulers()
    }
}
