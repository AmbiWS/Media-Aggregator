package ru.androidschool.intensiv.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class MovieDetailsViewModelFactory constructor(private val model: MovieDetailsViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass == MovieDetailsViewModel::class.java){
            model as T
        } else {
            throw IllegalStateException("Unknown entity")
        }
}