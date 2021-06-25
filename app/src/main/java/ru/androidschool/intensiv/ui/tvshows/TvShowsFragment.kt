package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBResponse
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

        getTvShows(TheMovieDBClient.apiClient.getPopularTvShows(MovieFinderApp.API_KEY, "ru", 1))
    }

    private fun getTvShows(call: Call<MovieDBResponse>) {

        call.enqueue(object : Callback<MovieDBResponse> {
            override fun onFailure(call: Call<MovieDBResponse>, t: Throwable) {
                Timber.e(t.toString())
            }

            override fun onResponse(call: Call<MovieDBResponse>, response: Response<MovieDBResponse>) {
                Timber.d(response.body()?.contentList.toString())

                if (response.code() == 200) {

                    val tvShowsList =
                        response.body()!!.contentList.map {
                            TvShowsItem(
                                it
                            ) { }
                        }.toList()

                    adapter.apply { addAll(tvShowsList) }
                }
            }
        })
    }
}
