package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.vo.MovieDetails
import ru.androidschool.intensiv.domain.repository.IMovieDetailsRepository

class MovieDetailsRepository : IMovieDetailsRepository {

    override fun getMovieDetails(id: Int): Single<MovieDetails> {
        return TheMovieDBClient.apiClient.getMovieDetails(id)
            .map { MovieMapper.toValueObject(it) }
    }
}
