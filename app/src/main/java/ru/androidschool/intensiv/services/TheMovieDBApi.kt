package ru.androidschool.intensiv.services

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.*

interface TheMovieDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int): Observable<MovieDBResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Observable<MovieDBResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<MovieDBResponse>

    @GET("tv/popular")
    fun getPopularTvShows(@Query("page") page: Int): Observable<MovieDBResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Observable<MovieDetails>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") id: Int): Observable<MovieCredits>
}
