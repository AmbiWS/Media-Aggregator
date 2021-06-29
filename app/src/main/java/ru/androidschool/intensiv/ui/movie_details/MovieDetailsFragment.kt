package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieCredits
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId: Int = arguments?.getInt("id") ?: 0
        val posterPath: String? = arguments?.getString("poster")

        posterPath?.let { detailsImagePoster.loadImage(it) }

        actors_recycleView.adapter = adapter.apply { }
        adapter.clear()
        getMovieCredits(TheMovieDBClient.apiClient.getMovieCredits(movieId))
        getMovieDetails(TheMovieDBClient.apiClient.getMovieDetails(movieId))

        // TODO: Get favorite movie boolean from db
        /*if (isFavoriteMovie) {
            checkboxFavoriteMovie.isChecked = true
        }*/
    }

    private fun getMovieCredits(observable: Single<MovieCredits>) {

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(MovieCredits::actorsList)
            .subscribe(
                { i ->
                    i?.let {
                        i.toList().map {
                            activity?.runOnUiThread {
                                adapter.apply { add(ActorItem(it) {}) }
                            }
                        }
                    }
                },
                { e -> Timber.d("$e") })
    }

    private fun getMovieDetails(observable: Single<MovieDetails>) {

        observable.subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.newThread())
            .subscribe(
                { i ->
                    activity?.runOnUiThread {
                        textDetailsTitle.text = i.title
                        movie_details_rating.rating = i?.rating ?: 0.0F
                        textViewAboutMovie.text = i?.aboutMovie
                        textViewProduction.text = i?.productionList?.get(0)?.name ?: getString(R.string.production_missing)
                        textViewGenre.text = i?.genre?.get(0)?.name?.capitalize() ?: getString(R.string.genre_missing)
                        textViewYear.text = i?.date?.substring(0, 4) ?: getString(R.string.year_missing)
                    }
                },
                { e -> Timber.d("$e") })
    }
}
