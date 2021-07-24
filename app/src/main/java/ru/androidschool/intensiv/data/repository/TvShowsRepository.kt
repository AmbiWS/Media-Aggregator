package ru.androidschool.intensiv.data.repository

import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.repository.ITvShowsRepository

class TvShowsRepository : ITvShowsRepository {

    override suspend fun getTvShows(): List<Movie> {

        return MovieMapper.toValueObject(TheMovieDBClient.apiClient.getPopularTvShows())
    }
}
