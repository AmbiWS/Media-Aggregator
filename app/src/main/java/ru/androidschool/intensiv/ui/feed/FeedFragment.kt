package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBContent
import ru.androidschool.intensiv.data.MovieDBResponse
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        // TODO: Fix disappearing rating of movies in second card container
        movies_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        // Now Playing, Second Page
        createCardContainer(R.string.upcoming, TheMovieDBClient.apiClient.getNowPlayingMovies(2))

        // Top Rated
        createCardContainer(R.string.top_rated, TheMovieDBClient.apiClient.getTopRatedMovies(1))

        // Popular
        createCardContainer(R.string.popular, TheMovieDBClient.apiClient.getPopularMovies(1))
    }

    private fun openMovieDetails(movie: MovieDBContent) {
        val bundle = Bundle()
        Timber.d("MOVIE ID: %s", movie.id)
        bundle.putInt(KEY_ID, movie.id)
        bundle.putString(KEY_POSTER_PATH, "https://www.themoviedb.org/t/p/w220_and_h330_face/" + movie.posterPath)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun createCardContainer(titleAsResource: Int, call: Call<MovieDBResponse>) {

        call.enqueue(object : Callback<MovieDBResponse> {
            override fun onFailure(call: Call<MovieDBResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieDBResponse>, response: Response<MovieDBResponse>) {
                Timber.d(response.body()?.contentList.toString())

                if (response.code() == 200) {

                    val moviesList = listOf(
                        MainCardContainer(
                            titleAsResource,
                            response.body()!!.contentList.map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )

                    adapter.apply { addAll(moviesList) }
                }
            }
        })
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_ID = "id"
        const val KEY_POSTER_PATH = "poster"
        const val KEY_SEARCH = "search"
    }
}
