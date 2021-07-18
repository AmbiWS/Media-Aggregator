package ru.androidschool.intensiv.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieDetailsViewModelFactory (val model: MovieDetailsViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass == MovieDetailsViewModel::class.java){
            model as T
        } else {
            throw  IllegalStateException("Unknown entity")
        }
}