package ru.androidschool.intensiv.ui.search

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.movies_recycler_view
import kotlinx.android.synthetic.main.search_toolbar.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.extensions.EditTextExtensions.onChange
import ru.androidschool.intensiv.extensions.ObservableExtensions.animateOnLoading
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeAndObserveOnRetrofit
import ru.androidschool.intensiv.retrofit.TheMovieDBClient
import ru.androidschool.intensiv.ui.LoadingImageView
import ru.androidschool.intensiv.ui.feed.FeedFragment.Companion.KEY_SEARCH
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private lateinit var searchFragmentLoadingImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchFragmentLoadingImageView = LoadingImageView.getLoadingImage(this.requireActivity())

        val halfOfSecondMs: Long = 500
        val minLettersInWord = 3

        val source: PublishSubject<String> = PublishSubject.create()

        search_edit_text.onChange {
            source.onNext(it)
        }

        movies_recycler_view.adapter = adapter.apply { }
        adapter.clear()

        val searchTerm = requireArguments().getString(KEY_SEARCH)
        search_toolbar.setText(searchTerm)

        source.debounce(halfOfSecondMs, TimeUnit.MILLISECONDS)
            ?.map { x -> x.trim() }
            ?.filter { x -> x.length > minLettersInWord }
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

    private fun findMovie(observable: Single<MovieResponse>) {

        observable.subscribeAndObserveOnRetrofit().let {
            // TODO: remove runOnUiThread and fix CalledFromWrongThreadException
            activity?.runOnUiThread {
                it.animateOnLoading(searchFragmentLoadingImageView)
                    .map(MovieResponse::contentList)
                    .subscribe(
                        { i ->
                            i.toList().map {
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
                        },
                        { e -> Timber.d("$e") })
            }
        }
    }
}
