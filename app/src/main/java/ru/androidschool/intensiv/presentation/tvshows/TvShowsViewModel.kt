package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidschool.intensiv.data.repository.TvShowsRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import timber.log.Timber

class TvShowsViewModel : ViewModel() {

    private val tvShowsUseCase: MoviesUseCase = MoviesUseCase(TvShowsRepository())

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadMovies()
        }
    }

    fun getMovies() : LiveData<List<Movie>> {
        return movies
    }

    private fun loadMovies() {
        tvShowsUseCase.getMovies()
            .subscribeIoObserveMT()
            .subscribe(
                { i ->
                    movies.postValue(i)
                },
                { t ->
                    Timber.e(t, t.toString())
                })
    }
}