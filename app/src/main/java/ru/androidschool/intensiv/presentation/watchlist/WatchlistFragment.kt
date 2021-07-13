package ru.androidschool.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.domain.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.data.room.MovieDB

class WatchlistFragment : Fragment(R.layout.fragment_watchlist) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val mDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieDao = MovieDB.getInstance(requireContext())?.movieDao()

        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        mDisposable.add(movieDao?.getAll()
            ?.subscribeIoObserveMT()
            ?.subscribe {

                val movieContentList = it.map { i ->
                    MovieContent(i.title, 0.0, i.posterPath, i.id)
                }

                val moviesList = movieContentList.map {
                    MoviePreviewItem(
                        it
                    ) { movie -> }
                }.toList()

                movies_recycler_view?.let { it.adapter = adapter.apply { addAll(moviesList) } }
                mDisposable.clear()
            })
    }

    companion object {

        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
