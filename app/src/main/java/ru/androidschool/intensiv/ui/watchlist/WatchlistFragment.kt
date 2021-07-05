package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_watchlist.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.room.MovieDB

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
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                val moviesList = it.map {
                    MoviePreviewItem(
                        it
                    ) { movie -> }
                }.toList()

                movies_recycler_view?. let { it.adapter = adapter.apply { addAll(moviesList) } }
                mDisposable.dispose()
            })

    }

    companion object {

        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
