package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.domain.extensions.ImageViewExtensions.loadImage
import ru.androidschool.intensiv.data.room.MovieDBEntity
import ru.androidschool.intensiv.data.vo.Actor
import ru.androidschool.intensiv.presentation.LoadingProgressBar
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    // TODO: on ANR replace AndroidViewModel with ViewModel
    lateinit var model: MovieDetailsViewModel
    private lateinit var detailsFragmentLoadingImageView: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().applicationContext as MovieFinderApp).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        detailsFragmentLoadingImageView = LoadingProgressBar.getLoadingBar(this.requireActivity())

        val movieId: Int = arguments?.getInt("id") ?: 0
        val posterPath: String? = arguments?.getString("poster")
        val movieTitle: String = arguments.let { it?.getString("title") ?: "" }
        val currentMovie = MovieDBEntity(movieTitle, posterPath, movieId)

        posterPath?.let { detailsImagePoster.loadImage(it) }

        actors_recycleView.adapter = adapter.apply { }
        adapter.clear()

        val modelFactory = MovieDetailsViewModelFactory(MovieDetailsViewModel(movieId, requireActivity().application))
        model = ViewModelProvider(this, modelFactory).get(MovieDetailsViewModel::class.java)

        checkboxFavoriteMovie.setOnCheckedChangeListener { _, isFavorite ->
            Timber.d("Current movie: %s", currentMovie)

            if (isFavorite) {
                model.insert(currentMovie)
            } else {
                model.delete(currentMovie)
            }
        }

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
