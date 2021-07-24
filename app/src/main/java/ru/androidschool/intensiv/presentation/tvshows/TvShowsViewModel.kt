package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.data.repository.TvShowsRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.TvShowsUseCase
import java.lang.Exception

class TvShowsViewModel : ViewModel() {

    private val tvShowsUseCase: TvShowsUseCase = TvShowsUseCase(TvShowsRepository())

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }

    init {
        loadMovies()
    }

    private fun loadMovies() {

        viewModelScope.launch {
            try {
                movies.value = tvShowsUseCase.getTvShows()
            } catch (e: Exception) {

            }
        }
    }
}
