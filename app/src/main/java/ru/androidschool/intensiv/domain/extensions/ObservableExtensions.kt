package ru.androidschool.intensiv.domain.extensions

import android.view.ViewGroup
import android.widget.ProgressBar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object ObservableExtensions {

    fun <T> Single<T>.subscribeIoObserveMT(): Single<T> {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Flowable<T>.subscribeIoObserveMT(): Flowable<T> {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun Completable.subscribeIoObserveMT(): Completable {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Single<T>.animateOnLoading(progressBar: ProgressBar): Single<T> {

        progressBar.visibility = ViewGroup.VISIBLE
        return this.doOnTerminate { progressBar.visibility = ViewGroup.GONE }
    }
}
