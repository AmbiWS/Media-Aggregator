package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.MovieDetails

interface MovieDetailsRepository {
    fun getMovieDetails(id: Int): Single<MovieDetails>
}