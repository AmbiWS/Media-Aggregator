package ru.androidschool.intensiv.presentation.movie_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.repository.MovieCreditsRepository
import ru.androidschool.intensiv.data.repository.MovieDetailsRepository
import ru.androidschool.intensiv.data.room.MovieDB
import ru.androidschool.intensiv.data.room.MovieDBEntity
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.data.vo.MovieDetails
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.domain.usecase.MovieCreditsUseCase
import ru.androidschool.intensiv.domain.usecase.MovieDetailsUseCase
import timber.log.Timber

class MovieDetailsViewModel(
    var id: Int, application: Application
) : AndroidViewModel(application) {

    private val movieCreditsUseCase: MovieCreditsUseCase =
        MovieCreditsUseCase(MovieCreditsRepository())
    private val movieDetailsUseCase: MovieDetailsUseCase =
        MovieDetailsUseCase(MovieDetailsRepository())

    private val movieDao = MovieDB.getInstance(application)?.movieDao()
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
        movieDao?.insertMovie(movie)
            ?.subscribeIoObserveMT()
            ?.doOnError { t: Throwable? -> Timber.d("Movie insert error -> %s", t.toString()) }
            ?.subscribe { Timber.d("Movie inserted") }
    }

    fun delete(movie: MovieDBEntity) {
        movieDao?.deleteMovie(movie)
            ?.subscribeIoObserveMT()
            ?.doOnError { t: Throwable? -> Timber.d("Movie delete error -> %s", t.toString()) }
            ?.subscribe { Timber.d("Movie deleted") }
    }

    init {
        loadDetails()
        loadCredits()
    }

    override fun onCleared() {
        disposables?.clear()
        super.onCleared()
    }

    private fun loadDetails() {
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

    private fun loadCredits() {
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