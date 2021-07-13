package ru.androidschool.intensiv.presentation.tvshows

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidschool.intensiv.data.repository.TvShowsRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import timber.log.Timber

class TvShowsViewModel : ViewModel() {

    private val tvShowsUseCase: MoviesUseCase = MoviesUseCase(TvShowsRepository())

    private var tvShowsFragmentLoadingImageView: ProgressBar? = null

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadMovies()
        }
    }

    override fun onCleared() {
        tvShowsFragmentLoadingImageView = null
        super.onCleared()
    }

    fun setProgressBar(progressBar: ProgressBar) {
        tvShowsFragmentLoadingImageView = progressBar
    }

    fun getMovies() : LiveData<List<Movie>> {
        return movies
    }

    private fun loadMovies() {
        tvShowsFragmentLoadingImageView?.let {
            tvShowsUseCase.getMovies()
                .subscribeIoObserveMT()
                .animateOnLoading(it)
                .subscribe(
                    { i ->
                        movies.postValue(i)
                    },
                    { t ->
                        Timber.e(t, t.toString())
                    })
        }
    }
}