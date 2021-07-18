package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.Actor
import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.data.dto.MovieCredits
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

    fun toValueObject(dto: MovieCredits): List<ru.androidschool.intensiv.data.vo.Actor>? {
        return dto.actorsList?.map { toValueObject(it) }
    }

    fun toValueObject(dto: Actor): ru.androidschool.intensiv.data.vo.Actor {

        return ru.androidschool.intensiv.data.vo.Actor(
            name = dto.name,
            id = dto.id,
            photoPath = dto.photoPath
        )
    }
}
