package ru.androidschool.intensiv.domain

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Movie

interface MoviesRepository {
    fun getMovies(): Single<List<Movie>>
}