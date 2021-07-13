package ru.androidschool.intensiv.presentation.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<V> {

    protected var view: V? = null
    private var disposable: CompositeDisposable? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun addDisposable(disposable: Disposable) {
        this.disposable?.add(disposable)
    }

    fun clearCompositeDisposable() {
        this.disposable?.clear()
    }
}
