package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.domain.repository.IMovieCreditsRepository
import javax.inject.Inject

class MovieCreditsRepository @Inject constructor() : IMovieCreditsRepository {

    override fun getMovieCredits(id: Int): Single<List<Actor>> {
        return TheMovieDBClient.apiClient.getMovieCredits(id)
            .map { MovieMapper.toValueObject(it) }
    }
}
