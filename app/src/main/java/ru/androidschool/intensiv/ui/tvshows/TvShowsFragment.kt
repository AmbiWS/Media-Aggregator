package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.ui.watchlist.MoviePreviewItem
import ru.androidschool.intensiv.ui.watchlist.WatchlistFragment

class TvShowsFragment : Fragment(R.layout.fragment_tv_shows) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvshows_recycler_view.layoutManager = GridLayoutManager(context, 1)
        tvshows_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val moviesList =
            MockRepository.getMovies().map {
                TvShowsItem(
                    it
                ) { movie -> }
            }.toList()

        tvshows_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    companion object {

        @JvmStatic
        fun newInstance() = TvShowsFragment()
    }

}