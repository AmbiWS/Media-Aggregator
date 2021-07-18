package ru.androidschool.intensiv.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.androidschool.intensiv.data.vo.Actor

interface MovieCreditsRepository {
    fun getMovieCredits(id: Int): Single<List<Actor>>
}