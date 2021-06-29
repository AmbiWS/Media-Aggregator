package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBResponse
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeAndObserveOnRetrofit
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.fragment_tv_shows) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvshows_recycler_view.layoutManager = LinearLayoutManager(context)

        tvshows_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        getTvShows(TheMovieDBClient.apiClient.getPopularTvShows(1))
    }

    private fun getTvShows(observable: Single<MovieDBResponse>) {

        observable.subscribeAndObserveOnRetrofit()
            .map(MovieDBResponse::contentList)
            .subscribe(
                { i ->
                    i.toList().map {
                        adapter.apply { add(TvShowsItem(it) {}) }
                    }
                },
                { e -> Timber.d("$e") })
    }
}
