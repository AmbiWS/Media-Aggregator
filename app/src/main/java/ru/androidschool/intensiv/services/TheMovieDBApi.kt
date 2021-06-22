package ru.androidschool.intensiv.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.MovieResponse

interface TheMovieDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>
}