package ru.androidschool.intensiv.domain.usecase

import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.ITvShowsRepository

class TvShowsUseCase(private val repository: ITvShowsRepository) {

    suspend fun getTvShows(): List<Movie> {

        return repository.getTvShows()
    }
}
