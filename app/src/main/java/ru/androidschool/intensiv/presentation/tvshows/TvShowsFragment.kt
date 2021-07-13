package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.vo.Movie
import ru.androidschool.intensiv.presentation.LoadingProgressBar

class TvShowsFragment : Fragment(R.layout.fragment_tv_shows) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var tvShowsFragmentLoadingImageView: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowsFragmentLoadingImageView = LoadingProgressBar.getLoadingBar(this.requireActivity())

        tvshows_recycler_view.layoutManager = LinearLayoutManager(context)

        tvshows_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        val model: TvShowsViewModel by viewModels()
        model.setProgressBar(tvShowsFragmentLoadingImageView)
        model.getMovies().observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
            movies.map {
                adapter.apply { add(TvShowsItem(it) {}) }
            }
        })
    }
}
