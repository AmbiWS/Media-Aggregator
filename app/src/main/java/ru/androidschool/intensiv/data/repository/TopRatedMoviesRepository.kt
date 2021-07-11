package ru.androidschool.intensiv.data.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.mappers.MovieMapper
import ru.androidschool.intensiv.data.network.TheMovieDBApi
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.MoviesRepository

class TopRatedMoviesRepository : MoviesRepository {

    override fun getMovies(): Single<List<Movie>> {
        return TheMovieDBClient.apiClient.getTopRatedMovies()
            .map { MovieMapper.toValueObject(it) }
    }
}