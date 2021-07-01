package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeAndObserveOnRetrofit
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.ui.LoadingImageView
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.fragment_tv_shows) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var tvShowsFragmentLoadingImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowsFragmentLoadingImageView = LoadingImageView.getLoadingImage(this.requireActivity())

        tvshows_recycler_view.layoutManager = LinearLayoutManager(context)

        tvshows_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        getTvShows(TheMovieDBClient.apiClient.getPopularTvShows(1))
    }

    private fun getTvShows(observable: Single<MovieResponse>) {

        observable.subscribeAndObserveOnRetrofit()
            .animateOnLoading(tvShowsFragmentLoadingImageView)
            .map(MovieResponse::contentList)
            .subscribe(
                { i ->
                    i.toList().map {
                        adapter.apply { add(TvShowsItem(it) {}) }
                    }
                },
                { e -> Timber.d("$e") })
    }
}
