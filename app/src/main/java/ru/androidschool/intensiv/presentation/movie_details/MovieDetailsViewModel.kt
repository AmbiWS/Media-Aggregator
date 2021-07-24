package ru.androidschool.intensiv.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.room.MovieDBEntity
import ru.androidschool.intensiv.data.room.dao.MovieDao
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.data.vo.MovieDetails
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MovieCreditsUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailsUseCase
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    val dao: MovieDao?,
    val movieCreditsUseCase: MovieCreditsUseCase,
    val movieDetailsUseCase: MovieDetailsUseCase
) : ViewModel() {

    private var disposables: CompositeDisposable? = null

    private val details: MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }

    private val credits: MutableLiveData<List<Actor>> by lazy {
        MutableLiveData<List<Actor>>()
    }

    private val isLoaded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getDetails(): LiveData<MovieDetails> {
        return details
    }

    fun getCredits(): LiveData<List<Actor>> {
        return credits
    }

    fun getIsLoaded(): LiveData<Boolean> {
        return isLoaded
    }

    fun insert(movie: MovieDBEntity) {
        dao?.insertMovie(movie)
            ?.subscribeIoObserveMT()
            ?.doOnError { t: Throwable? -> Timber.d("Movie insert error -> %s", t.toString()) }
            ?.subscribe { Timber.d("Movie inserted") }
    }

    fun delete(movie: MovieDBEntity) {
        dao?.deleteMovie(movie)
            ?.subscribeIoObserveMT()
            ?.doOnError { t: Throwable? -> Timber.d("Movie delete error -> %s", t.toString()) }
            ?.subscribe { Timber.d("Movie deleted") }
    }

    override fun onCleared() {
        disposables?.clear()
        super.onCleared()
    }

    fun load(id: Int) {
        loadDetails(id)
        loadCredits(id)
    }

    private fun loadDetails(id: Int) {
        val disp = movieDetailsUseCase.getMovieDetails(id)
            .subscribeIoObserveMT()
            .animateOnLoading(isLoaded)
            .subscribe(
                { i ->
                    details.setValue(i)
                },
                { t ->
                    Timber.e(t, t.toString())
                })

        disposables?.add(disp)
    }

    private fun loadCredits(id: Int) {
        val disp = movieCreditsUseCase.getMovieCredits(id)
            .subscribeIoObserveMT()
            .subscribe(
                { i ->
                    credits.setValue(i)
                },
                { t ->
                    Timber.e(t, t.toString())
                })

        disposables?.add(disp)
    }
}
