package ru.androidschool.intensiv.presentation.feed

import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class FeedPresenter(
    private val topRatedUseCase: MoviesUseCase,
    private val popularUseCase: MoviesUseCase,
    private val nowPlayingUseCase: MoviesUseCase
) :
    BasePresenter<FeedPresenter.FeedView>() {

    fun getMovies() {

        topRatedUseCase.getMovies()
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