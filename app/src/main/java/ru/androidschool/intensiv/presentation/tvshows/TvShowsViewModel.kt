package ru.androidschool.intensiv.presentation.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.androidschool.intensiv.data.vo.Movie

class TvShowsViewModel : ViewModel() {

    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
            loadMovies()
        }
    }

    fun getMovies() : LiveData<List<Movie>> {
        return movies
    }

    private fun loadMovies() {

    }
}