package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.data.dto.MovieResponse
import ru.androidschool.intensiv.data.vo.Movie

object MovieMapper {

    fun toValueObject(dto: MovieResponse): List<Movie> {
        return dto.contentList.map { toValueObject(it) }
    }

    fun toValueObject(dto: MovieContent): Movie {

        return Movie(
            id = dto.id,
            title = dto.title,
            voteAverage = dto.voteAverage,
            posterPath = dto.posterPath
        )
    }
}
