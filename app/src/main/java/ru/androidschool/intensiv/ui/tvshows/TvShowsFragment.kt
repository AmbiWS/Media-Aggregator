package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBResponse
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.ui.feed.MainCardContainer
import ru.androidschool.intensiv.ui.feed.MovieItem
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

        observable.subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.newThread())
            .map(MovieDBResponse::contentList)
            .subscribe(
                { i ->
                    i.toList().map {
                        TvShowsItem(it) { }
                    }.toList().let {
                        activity?.runOnUiThread {
                            adapter.apply {
                                add(
                                    MainCardContainer(
                                        titleAsResource,
                                        it
                                    )
                                )
                            }
                        }
                    }
                },
                { e -> Timber.d("$e") })
    }

    /*private fun getTvShows(call: Call<MovieDBResponse>) {

        call.enqueue(object : Callback<MovieDBResponse> {
            override fun onFailure(call: Call<MovieDBResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieDBResponse>, response: Response<MovieDBResponse>) {
                Timber.d(response.body()?.contentList.toString())

                if (response.isSuccessful) {

                    val tvShowsList =
                        response.body()?.let {
                            response.body()?.contentList?.map {
                                TvShowsItem(
                                    it
                                ) { }
                            }
                        }?.toList()

                    adapter.apply { tvShowsList?.let { addAll(it) } }
                }
            }
        })
    }*/
}
