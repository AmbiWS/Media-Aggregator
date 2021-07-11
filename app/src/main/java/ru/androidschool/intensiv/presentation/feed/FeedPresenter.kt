package ru.androidschool.intensiv.presentation.feed

import android.widget.ProgressBar
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function3
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import ru.androidschool.intensiv.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class FeedPresenter(
    private val topRatedUseCase: MoviesUseCase,
    private val popularUseCase: MoviesUseCase,
    private val nowPlayingUseCase: MoviesUseCase
) :
    BasePresenter<FeedPresenter.FeedView>() {

    fun getMovies(progressBar: ProgressBar) {

        getMovies(nowPlayingUseCase, topRatedUseCase, popularUseCase, progressBar)
    }

    private fun getMovies(
        nowPlayingUseCase: MoviesUseCase,
        topRatedUseCase: MoviesUseCase,
        popularUseCase: MoviesUseCase,
        progressBar: ProgressBar
    ) {

        val nowPlaying: Single<List<Movie>> = nowPlayingUseCase.getMovies()
        val topRated: Single<List<Movie>> = topRatedUseCase.getMovies()
        val popular: Single<List<Movie>> = popularUseCase.getMovies()

        Single.zip(nowPlaying, topRated, popular,
            Function3<List<Movie>, List<Movie>, List<Movie>, List<List<Movie>>> { nowPlayingResp: List<Movie>,
                                                                                  topRatedResp: List<Movie>,
                                                                                  popularResp: List<Movie> ->

                listOf(nowPlayingResp, topRatedResp, popularResp)
            }).subscribeIoObserveMT()
            .animateOnLoading(progressBar)
            .subscribe(
                { i ->
                    view?.linkFeedData(i)
                },
                {t ->
                    Timber.e(t, t.toString())
                    view?.showEmptyMovies()
                })

    }

    interface FeedView {
        fun showMovies(movies: List<Movie>, titleRes: Int)
        fun linkFeedData(feed: List<List<Movie>>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError()
    }
}