package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.data.repository.TvShowsRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MoviesUseCase
import ru.androidschool.intensiv.domain.usecase.TvShowsUseCase
import timber.log.Timber
import java.lang.Exception

class TvShowsViewModel : ViewModel() {

    private val tvShowsUseCase: TvShowsUseCase = TvShowsUseCase(TvShowsRepository())
    private var disposables: CompositeDisposable? = null

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    private val isLoaded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }

    fun getIsLoaded(): LiveData<Boolean> {
        return isLoaded
    }

    init {
        loadMovies()
    }

    override fun onCleared() {
        disposables?.clear()
        super.onCleared()
    }

    private fun loadMovies() {

        /*viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }*/

        movies.value = tvShowsUseCase.getTvShows()
            /*.subscribeIoObserveMT()
            .animateOnLoading(isLoaded)
            .subscribe(
                { i ->
                    movies.setValue(i)
                },
                { t ->
                    Timber.e(t, t.toString())
                })

        disposables?.add(disp)*/
    }
}
