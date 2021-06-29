package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.movies_recycler_view
import kotlinx.android.synthetic.main.search_toolbar.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBResponse
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val source = Observable.create(ObservableOnSubscribe<String> { emitter ->

            val watcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(p0.toString())
                        Timber.d(p0.toString())
                    }
                }
            }

            emitter.setCancellable { search_edit_text.removeTextChangedListener(watcher) }
            search_edit_text.addTextChangedListener(watcher)
        })

        movies_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        val searchTerm = requireArguments().getString(KEY_SEARCH)
        search_toolbar.setText(searchTerm)

        source?.debounce(500, TimeUnit.MILLISECONDS)
            ?.skip(1)
            ?.map { x -> x.trim() }
            ?.filter { x -> x.length > 3 }
            ?.subscribe(object : Observer<String> {

                override fun onSubscribe(d: Disposable?) {
                    Timber.d("onSubscribe: %s", d.toString())
                    onNext(search_edit_text.text.toString())
                }

                override fun onNext(value: String?) {
                    Timber.d("onNext: %s", value)

                    activity?.runOnUiThread {
                        adapter.clear()
                    }

                    value?.let { TheMovieDBClient.apiClient.getMovieByQuery(1, it) }?.let {
                        findMovie(
                            it
                        )
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.d("onSubscribe: %s", e.toString())
                }

                override fun onComplete() {
                    Timber.d("onComplete")
                }
            })
    }

    private fun findMovie(observable: Single<MovieDBResponse>) {

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(MovieDBResponse::contentList)
            .subscribe(
                { i ->
                    i.toList().map {
                        activity?.runOnUiThread {
                            adapter.apply {
                                add(
                                    SearchItem(
                                        it.title +
                                                " (" + it.rating + ")"
                                    )
                                )
                            }
                            Timber.d(it.title)
                        }
                    }
                },
                { e -> Timber.d("$e") })
    }
}
