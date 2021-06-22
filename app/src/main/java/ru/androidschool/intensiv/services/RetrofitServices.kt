package ru.androidschool.intensiv.services

import retrofit2.Call
import retrofit2.http.GET
import ru.androidschool.intensiv.data.Movie

interface RetrofitServices {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(): Call<MutableList<Movie>>

}