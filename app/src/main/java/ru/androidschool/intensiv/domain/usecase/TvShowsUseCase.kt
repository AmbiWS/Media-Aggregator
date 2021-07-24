package ru.androidschool.intensiv.domain.usecase

import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.TvShowsRepository

class TvShowsUseCase(private val repository: TvShowsRepository) {

    fun getTvShows(): List<Movie> {

        return repository.getTvShows()
    }
}
