package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.data.dto.MovieResponse

object MovieMapper {

    fun toValueObject(dto: MovieResponse): List<MovieContent> {
        return dto.contentList.map { toValueObject(it) }
    }

    fun toValueObject(dto: MovieContent): MovieContent {

        return MovieContent(
            id = dto.id,
            title = dto.title,
            voteAverage = dto.voteAverage,
            posterPath = dto.posterPath
        )
    }
}