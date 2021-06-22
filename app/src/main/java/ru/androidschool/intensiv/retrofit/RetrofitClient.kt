package ru.androidschool.intensiv.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.services.RetrofitServices

object RetrofitClient {

    private var retrofit: Retrofit? = null

    private const val TheMovieDB_BASE_URL = "https://api.themoviedb.org/3/"

    val retrofitService: RetrofitServices
        get() = getClient().create(RetrofitServices::class.java)

    private fun getClient(): Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(TheMovieDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}