package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.*
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

    fun toValueObject(dto: MovieDetails): ru.androidschool.intensiv.data.vo.MovieDetails {

        return ru.androidschool.intensiv.data.vo.MovieDetails(
            isFavoriteMovie = dto.isFavoriteMovie,
            title = dto.title,
            votePoints = dto.votePoints,
            aboutMovie = dto.aboutMovie,
            productionList = dto.productionList,
            genre = dto.genre,
            date = dto.date
        )
    }
}
