package ru.androidschool.intensiv.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.MovieCredits
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.TvShowsResponse

interface TheMovieDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<MovieResponse>

    @GET("tv/popular")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("language") language: String, @Query("page") page: Int): Call<TvShowsResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieDetails>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") id: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieCredits>

}