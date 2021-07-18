package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.MovieCredits
import ru.androidschool.intensiv.data.dto.MovieDetails
import ru.androidschool.intensiv.domain.extensions.ImageViewExtensions.loadImage
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.room.MovieDB
import ru.androidschool.intensiv.data.room.MovieDBEntity
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.presentation.LoadingProgressBar
import ru.androidschool.intensiv.presentation.tvshows.TvShowsItem
import ru.androidschool.intensiv.presentation.tvshows.TvShowsViewModel
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

        val movieDao = MovieDB.getInstance(requireContext())?.movieDao()
        val currentMovie = MovieDBEntity(movieTitle, posterPath, movieId)

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

        val modelFactory = MovieDetailsViewModelFactory(movieId)
        val model: MovieDetailsViewModel = ViewModelProviders.of(this, modelFactory).get(MovieDetailsViewModel::class.java)

        model.getDetails().observe(viewLifecycleOwner, Observer<ru.androidschool.intensiv.data.vo.MovieDetails> { details ->
            getDetails(details)
        })

        model.getCredits().observe(viewLifecycleOwner, Observer<List<Actor>> { actors ->
            getCredits(actors)
        })

        model.getIsLoaded().observe(viewLifecycleOwner, Observer<Boolean> {isLoaded ->
            if (isLoaded) {
                detailsFragmentLoadingImageView.visibility = ViewGroup.VISIBLE
            } else {
                detailsFragmentLoadingImageView.visibility = ViewGroup.GONE
            }
        })
    }

    private fun getCredits(credits: List<Actor>) {
        credits.map {
            adapter.apply { add(ActorItem(it) {}) }
        }
    }

    private fun getDetails(details: ru.androidschool.intensiv.data.vo.MovieDetails) {
        textDetailsTitle.text = details.title
        movie_details_rating.rating = details.rating
        textViewAboutMovie.text = details.aboutMovie
        textViewProduction.text = details.productionList[0].name
        textViewGenre.text = details.genre[0].name.capitalize()
        textViewYear.text = if (details.date.length >= 4) details.date.substring(0,4) else getString(R.string.year_missing)
    }
}
