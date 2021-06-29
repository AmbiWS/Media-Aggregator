package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function3
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBContent
import ru.androidschool.intensiv.data.MovieDBResponse
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeAndObserveOnRetrofit
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

    enum class FeedContent(val id: Int, val single: Single<MovieDBResponse>) {
        NOW_PLAYING(R.string.upcoming, TheMovieDBClient.apiClient.getNowPlayingMovies(2)),
        TOP_RATED(R.string.top_rated, TheMovieDBClient.apiClient.getTopRatedMovies(1)),
        POPULAR(R.string.popular, TheMovieDBClient.apiClient.getPopularMovies(1))
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

        val nowPlaying: Single<MovieDBResponse> = FeedContent.values()[0].single
        val topRated: Single<MovieDBResponse> = FeedContent.values()[1].single
        val popular: Single<MovieDBResponse> = FeedContent.values()[2].single

        Single.zip(
            nowPlaying,
            topRated,
            popular,
            Function3<MovieDBResponse, MovieDBResponse, MovieDBResponse, List<MovieDBResponse>> { nowPlayingResponse: MovieDBResponse,
                                                                                                  topRatedResponse: MovieDBResponse,
                                                                                                  popularResponse: MovieDBResponse ->

                listOf(nowPlayingResponse, topRatedResponse, popularResponse)

            }).subscribeAndObserveOnRetrofit()
            .subscribe { i -> linkFeedData(i) }
    }

    private fun linkFeedData(feed: List<MovieDBResponse>) {

        for (i in feed.indices) {

            feed.get(i).contentList.map {
                MovieItem(it) { movie ->
                    openMovieDetails(
                        movie
                    )
                }
            }.toList().let {
                adapter.apply {
                    add(
                        MainCardContainer(
                            FeedContent.values()[i].id,
                            it
                        )
                    )
                }
            }
        }
    }

    private fun openMovieDetails(movie: MovieDBContent) {
        val bundle = Bundle()
        Timber.d("MOVIE ID: %s", movie.id)
        bundle.putInt(KEY_ID, movie.id)
        bundle.putString(
            KEY_POSTER_PATH,
            BuildConfig.POSTER_PATH + movie.posterPath
        )
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

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_ID = "id"
        const val KEY_POSTER_PATH = "poster"
        const val KEY_SEARCH = "search"
    }
}
