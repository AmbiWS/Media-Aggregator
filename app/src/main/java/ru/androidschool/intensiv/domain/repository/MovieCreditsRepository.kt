package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.dto.Actor

interface MovieCreditsRepository {
    fun getMovieCredits(): Single<List<Actor>>
}