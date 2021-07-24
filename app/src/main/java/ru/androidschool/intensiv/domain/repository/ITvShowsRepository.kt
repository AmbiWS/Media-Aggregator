package ru.androidschool.intensiv.domain.repository

import ru.androidschool.intensiv.data.vo.Movie

interface ITvShowsRepository {
    suspend fun getTvShows(): List<Movie>
}
