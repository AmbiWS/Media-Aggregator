package ru.androidschool.intensiv.extensions

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.ui.LoadingProgressBar

object ObservableExtensions {

    fun <T> Single<T>.subscribeAndObserveOnRetrofit(): Single<T> {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Single<T>.animateOnLoading(progressBar: ProgressBar): Single<T> {

        progressBar.visibility = ViewGroup.VISIBLE
        return this.doOnTerminate { progressBar.visibility = ViewGroup.GONE }
    }
}
