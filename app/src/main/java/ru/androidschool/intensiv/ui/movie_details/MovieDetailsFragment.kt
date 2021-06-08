package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.ExtensionMethods.loadImage
import ru.androidschool.intensiv.data.MockRepositoryMovieDetails

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieDetails = MockRepositoryMovieDetails.getDetails()

        // TODO: Get title name & title rating from Bundle, after Retrofit refactoring

        detailsImagePoster.loadImage("https://assets.vogue.in/photos/5fb498ce49cee77f06f7e19f/16:9/w_2400,h_1350,c_limit/The-Queens-Gambit-vogue-171120-courtesy-Netflix-4.jpg")

        textDetailsTitle.text = movieDetails.title
        movie_details_rating.rating = movieDetails.rating

        // TODO: Add logic to set/unset favorite movie onClick

        if (movieDetails.isFavoriteMovie) {
            checkboxFavoriteMovie.isChecked = true
        }

        textViewAboutMovie.text = movieDetails.aboutMovie

        val actorsList =
            movieDetails.actors?.map {
                ActorItem(
                    it
                ) { actor -> }
            }?.toList()
        actors_recycleView.adapter = adapter.apply { actorsList?.let { addAll(it) } }

        textViewProduction.text = movieDetails.production
        textViewGenre.text = movieDetails.genre
        textViewYear.text = movieDetails.year.toString()
    }
}
