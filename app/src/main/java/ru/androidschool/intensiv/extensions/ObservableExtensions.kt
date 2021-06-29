package ru.androidschool.intensiv.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object ObservableExtensions {

    fun <T> Single<T>.subscribeAndObserveOnRetrofit(): Single<T> {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}