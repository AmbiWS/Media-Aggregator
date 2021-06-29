package ru.androidschool.intensiv.retrofit

import android.app.Activity
import android.widget.ImageView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.services.TheMovieDBApi

object TheMovieDBClient {

    private const val BASE_URL = BuildConfig.BASE_URL

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        })
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DATABASE_API)
                .addQueryParameter("language", MovieFinderApp.LANGUAGE)
                .build()

            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        .build()

    val apiClient: TheMovieDBApi by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return@lazy retrofit.create(TheMovieDBApi::class.java)
    }
}
