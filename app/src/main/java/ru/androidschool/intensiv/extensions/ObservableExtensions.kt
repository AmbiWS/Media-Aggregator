package ru.androidschool.intensiv.extensions

import android.widget.ImageView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.ui.LoadingImageView

object ObservableExtensions {

    fun <T> Single<T>.subscribeAndObserveOnRetrofit(): Single<T> {

        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> Single<T>.animateOnLoading(imageView: ImageView): Single<T> {

        imageView.setImageResource(R.drawable.loading)
        imageView.startAnimation(LoadingImageView.getRotateStyle())
        return this.doOnTerminate{imageView.setImageDrawable(null)}

    }
}
