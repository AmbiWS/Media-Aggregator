package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.util.Log
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
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.data.MovieResponse
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

        // TODO: Disappearing rating of movies in second card container
        movies_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        // Now Playing, Second Page
        val nowPlayingMovies = TheMovieDBClient.apiClient.getNowPlayingMovies(MovieFinderApp.API_KEY, "ru", 2)

        nowPlayingMovies.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d(response.body()?.movies.toString())

                if (response.code() == 200) {

                    val playingNowMoviesList = listOf(
                        MainCardContainer(
                            R.string.upcoming,
                            response.body()!!.movies.map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )

                    adapter.apply { addAll(playingNowMoviesList) }
                }
            }
        })

        // Top Rated
        val topRatedMovies = TheMovieDBClient.apiClient.getTopRatedMovies(MovieFinderApp.API_KEY, "ru")

        topRatedMovies.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d(response.body()?.movies.toString())

                if (response.code() == 200) {

                    val topRatedMoviesList = listOf(
                        MainCardContainer(
                            R.string.top_rated,
                            response.body()!!.movies.map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )

                    adapter.apply { addAll(topRatedMoviesList) }
                }
            }
        })

        // Popular
        val popularMovies = TheMovieDBClient.apiClient.getPopularMovies(MovieFinderApp.API_KEY, "ru")

        popularMovies.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d(response.body()?.movies.toString())

                if (response.code() == 200) {

                    val popularMoviesList = listOf(
                        MainCardContainer(
                            R.string.popular,
                            response.body()!!.movies.map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )

                    adapter.apply { addAll(popularMoviesList) }
                }
            }
        })
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movie.title)
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

    // TODO: find better way, if exists
    private inline fun <reified T> createCardContainer(titleAsResource: Int, call: Call<T>, dataClass: Class<Any>) {

        val nowPlayingMovies = TheMovieDBClient.apiClient.getNowPlayingMovies(MovieFinderApp.API_KEY, "ru", 2)

        nowPlayingMovies.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Timber.d(response.body()?.movies.toString())

                if (response.code() == 200) {

                    val playingNowMoviesList = listOf(
                        MainCardContainer(
                            R.string.upcoming,
                            response.body()!!.movies.map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                        )
                    )

                    adapter.apply { addAll(playingNowMoviesList) }
                }
            }
        })

    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
        const val TAG = "FeedFragment"
    }
}
