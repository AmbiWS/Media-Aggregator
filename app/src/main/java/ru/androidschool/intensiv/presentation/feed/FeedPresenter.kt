package ru.androidschool.intensiv.presentation.feed

import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.TopRatedMoviesUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class FeedPresenter(private val useCase: TopRatedMoviesUseCase) :
    BasePresenter<FeedPresenter.FeedView>() {

    fun getMovies() {

        useCase.getMovies()
            .subscribe(
                {
                    view?.showMovies(it)
                },
                { t ->
                    Timber.e(t, t.toString())
                    view?.showEmptyMovies()
                })


    }

    interface FeedView {
        fun showMovies(movies: List<Movie>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}