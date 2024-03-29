package ru.androidschool.intensiv.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.dto.MovieCredits
import ru.androidschool.intensiv.data.dto.MovieDetails
import ru.androidschool.intensiv.data.dto.MovieResponse

interface TheMovieDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int = 2): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int = 1): Single<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int = 1): Single<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page: Int = 1): MovieResponse

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") id: Int): Single<MovieCredits>

    @GET("search/movie")
    fun getMovieByQuery(@Query("page") page: Int = 1, @Query("query") query: String): Single<MovieResponse>
}
