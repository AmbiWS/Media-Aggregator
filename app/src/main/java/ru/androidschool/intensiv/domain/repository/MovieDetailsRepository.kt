package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.dto.MovieDetails

interface MovieDetailsRepository {
    fun getMovieDetails(): Single<MovieDetails>
}