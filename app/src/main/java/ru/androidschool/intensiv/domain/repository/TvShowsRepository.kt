package ru.androidschool.intensiv.domain.repository

import ru.androidschool.intensiv.data.vo.Movie

interface TvShowsRepository {
    fun getTvShows(): List<Movie>
}