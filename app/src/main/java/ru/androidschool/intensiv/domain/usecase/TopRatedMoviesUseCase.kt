package ru.androidschool.intensiv.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.domain.usecase.SchedulersExtension.applySchedulers

class TopRatedMoviesUseCase(private val repository: MoviesRepository) {

    fun getMovies(): Single<List<Movie>> {
        return repository.getMovies()
            .applySchedulers()
    }
}