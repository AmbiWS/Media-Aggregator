package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieCredits
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import timber.log.Timber
import java.util.*

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId: Int = arguments?.getInt("id") ?: 0
        val posterPath: String? = arguments?.getString("poster")

        if (posterPath != null) {
            detailsImagePoster.loadImage(posterPath)
        }

        getMovieCredits(TheMovieDBClient.apiClient.getMovieCredits(movieId, MovieFinderApp.API_KEY, "ru"))
        getMovieDetails(TheMovieDBClient.apiClient.getMovieDetails(movieId, MovieFinderApp.API_KEY, "ru"))

        // TODO: Get favorite movie boolean from db
        /*if (isFavoriteMovie) {
            checkboxFavoriteMovie.isChecked = true
        }*/

    }

    private fun getMovieCredits(call: Call<MovieCredits>) {

        call.enqueue(object : Callback<MovieCredits> {
            override fun onFailure(call: Call<MovieCredits>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieCredits>, response: Response<MovieCredits>) {
                Timber.d(response.body().toString())

                if (response.code() == 200) {

                    val actorsList =
                        response.body()!!.actorsList?.map {
                            ActorItem(
                                it
                            ) { }
                        }?.toList()

                    actors_recycleView.adapter = adapter.apply { actorsList?.let { addAll(it) } }

                }
            }
        })

    }


    private fun getMovieDetails(call: Call<MovieDetails>) {

        call.enqueue(object : Callback<MovieDetails> {
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                Timber.d(response.body().toString())

                if (response.code() == 200) {

                    textDetailsTitle.text = response.body()!!.title
                    movie_details_rating.rating = response.body()!!.rating
                    textViewAboutMovie.text = response.body()!!.aboutMovie
                    textViewProduction.text = response.body()!!.productionList.get(0).name
                    textViewGenre.text = response.body()!!.genre.get(0).name.capitalize()
                    textViewYear.text = response.body()!!.date.substring(0, 4)

                }
            }
        })

    }
}
