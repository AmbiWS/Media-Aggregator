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

    val NOW_PLAYING_MOVIES_POSITION: Int = 1
    val TOP_RATED_MOVIES_POSITION: Int = 2
    val POPULAR_MOVIES_POSITION: Int = 3

    fun getMovies() {

        getMovies(nowPlayingUseCase, NOW_PLAYING_MOVIES_POSITION)
        getMovies(topRatedUseCase, TOP_RATED_MOVIES_POSITION)
        getMovies(popularUseCase, POPULAR_MOVIES_POSITION)

    }

    private fun getMovies(useCase: MoviesUseCase, titleResPosition: Int) {
        useCase.getMovies()
            .subscribe(
                {
                    view?.showMovies(it, titleResPosition)
                },
                { t ->
                    Timber.e(t, t.toString())
                    view?.showEmptyMovies()
                })
    }

    interface FeedView {
        fun showMovies(movies: List<Movie>, titleRes: Int)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}