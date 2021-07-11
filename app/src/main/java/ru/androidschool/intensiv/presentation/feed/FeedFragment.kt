package ru.androidschool.intensiv.presentation.feed

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.ProgressBar
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
import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.data.dto.MovieResponse
import ru.androidschool.intensiv.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.data.network.TheMovieDBClient
import ru.androidschool.intensiv.data.repository.TopRatedMoviesRepository
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.domain.usecase.TopRatedMoviesUseCase
import ru.androidschool.intensiv.presentation.LoadingProgressBar
import ru.androidschool.intensiv.presentation.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment), FeedPresenter.FeedView {

    private val presenter: FeedPresenter by lazy {
        FeedPresenter(TopRatedMoviesUseCase(TopRatedMoviesRepository()))
    }

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

    enum class FeedContent(val id: Int, val single: Single<MovieResponse>) {
        NOW_PLAYING(R.string.upcoming, TheMovieDBClient.apiClient.getNowPlayingMovies()),
        TOP_RATED(R.string.top_rated, TheMovieDBClient.apiClient.getTopRatedMovies()),
        POPULAR(R.string.popular, TheMovieDBClient.apiClient.getPopularMovies())
    }

    private lateinit var feedFragmentLoadingImageView: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        feedFragmentLoadingImageView = LoadingProgressBar.getLoadingBar(this.requireActivity())

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > MIN_LENGTH) {
                openSearch(it.toString())
            }
        }

        // TODO: Fix disappearing rating of movies in second card container
        movies_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        /*val nowPlaying: Single<MovieResponse> = FeedContent.NOW_PLAYING.single
        val topRated: Single<MovieResponse> = FeedContent.TOP_RATED.single
        val popular: Single<MovieResponse> = FeedContent.POPULAR.single

        Single.zip(
            nowPlaying,
            topRated,
            popular,
            Function3<MovieResponse, MovieResponse, MovieResponse, List<MovieResponse>> { nowPlayingResponse: MovieResponse,
                                                                                          topRatedResponse: MovieResponse,
                                                                                          popularResponse: MovieResponse ->

                listOf(nowPlayingResponse, topRatedResponse, popularResponse)
            }).subscribeIoObserveMT()
            .animateOnLoading(feedFragmentLoadingImageView)
            .subscribe { i -> linkFeedData(i) }*/

        presenter.getMovies()
    }



    /*private fun linkFeedData(feed: List<MovieResponse>) {

        for (i in feed.indices) {

            feed[i].contentList.map {
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
    }*/

    private fun openMovieDetails(movie: MovieContent) {
        val bundle = Bundle()
        Timber.d("MOVIE ID: %s", movie.id)
        bundle.putInt(KEY_ID, movie.id)
        bundle.putString(
            KEY_POSTER_PATH,
            BuildConfig.POSTER_PATH + movie.posterPath
        )
        bundle.putString(
            KEY_TITLE,
            movie.title
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
        const val KEY_TITLE = "title"
    }

    override fun showMovies(movies: List<Movie>) {
        movies_recycler_view.adapter = adapter.apply {
            addAll(
                listOf(
                    MainCardContainer(
                        title = FeedContent.TOP_RATED.id,
                        items = movies.map { MovieItem(it, {}) }.toList()
                    )
                )
            )
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showEmptyMovies() {

    }

    override fun showError() {

    }
}
