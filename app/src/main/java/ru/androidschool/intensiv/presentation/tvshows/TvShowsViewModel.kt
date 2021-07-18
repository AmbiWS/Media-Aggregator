package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.repository.TvShowsRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import timber.log.Timber

class TvShowsViewModel : ViewModel() {

    private val tvShowsUseCase: MoviesUseCase = MoviesUseCase(TvShowsRepository())
    private var disposables: CompositeDisposable? = null

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadMovies()
        }
    }

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }

    private val isLoaded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getIsLoaded(): LiveData<Boolean> {
        return isLoaded
    }

    private fun loadMovies() {
        val disp = tvShowsUseCase.getMovies()
            .subscribeIoObserveMT()
            .animateOnLoading(isLoaded)
            .subscribe(
                { i ->
                    movies.postValue(i)
                    disposables?.clear()
                },
                { t ->
                    Timber.e(t, t.toString())
                })

        disposables?.add(disp)
    }
}
