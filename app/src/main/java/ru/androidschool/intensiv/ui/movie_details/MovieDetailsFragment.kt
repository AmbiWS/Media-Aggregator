package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieContent
import ru.androidschool.intensiv.data.MovieCredits
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage
import ru.androidschool.intensiv.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.room.MovieDB
import ru.androidschool.intensiv.ui.LoadingProgressBar
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var detailsFragmentLoadingImageView: ProgressBar

    private val mDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsFragmentLoadingImageView = LoadingProgressBar.getLoadingBar(this.requireActivity())

        val movieId: Int = arguments?.getInt("id") ?: 0
        val posterPath: String? = arguments?.getString("poster")
        val movieTitle: String = arguments.let { it?.getString("title") ?: "" }

        posterPath?.let { detailsImagePoster.loadImage(it) }

        actors_recycleView.adapter = adapter.apply { }
        adapter.clear()
        getMovieCredits(TheMovieDBClient.apiClient.getMovieCredits(movieId))
        getMovieDetails(TheMovieDBClient.apiClient.getMovieDetails(movieId))

        val movieDao = MovieDB.getInstance(requireContext())?.movieDao()
        val currentMovie = MovieContent(movieTitle, 0.0, posterPath, movieId)

        checkboxFavoriteMovie.setOnCheckedChangeListener { buttonView, isFavorite ->

            Timber.d("Favorite movie: %s", isFavorite)
            Timber.d("Current movie: %s", currentMovie)

            if (isFavorite) {

                mDisposable.add(
                    movieDao?.insertMovie(currentMovie)
                        ?.subscribeIoObserveMT()
                        ?.doOnError { t: Throwable? -> Timber.d("Movie insert error -> %s", t.toString()) }
                        ?.subscribe { Timber.d("Movie inserted") }
                )
            } else {

                mDisposable.add(
                    movieDao?.deleteMovie(currentMovie)
                        ?.subscribeIoObserveMT()
                        ?.doOnError { t: Throwable? -> Timber.d("Movie delete error -> %s", t.toString()) }
                        ?.subscribe { Timber.d("Movie deleted") }
                )
            }
        }
    }

    private fun getMovieCredits(observable: Single<MovieCredits>) {

        observable.subscribeIoObserveMT()
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

        observable.subscribeIoObserveMT()
            .animateOnLoading(detailsFragmentLoadingImageView)
            .subscribe(
                { i ->
                    textDetailsTitle.text = i.title
                    movie_details_rating.rating = i?.rating ?: 0.0F
                    textViewAboutMovie.text = i?.aboutMovie
                    textViewProduction.text =
                        i?.productionList?.get(0)?.name ?: getString(R.string.production_missing)
                    textViewGenre.text =
                        i?.genre?.get(0)?.name?.capitalize() ?: getString(R.string.genre_missing)
                    textViewYear.text = if (i?.date?.length ?: 0 >= 4) i?.date?.substring(
                        0,
                        4
                    ) else getString(R.string.year_missing)
                },
                { e -> Timber.d("$e") })
    }
}
