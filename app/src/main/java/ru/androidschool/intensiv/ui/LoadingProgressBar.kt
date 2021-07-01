package ru.androidschool.intensiv.ui

import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
import ru.androidschool.intensiv.R

object LoadingProgressBar {

    private val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    fun getLoadingBar(activity: FragmentActivity): ProgressBar {

        val progressBar = ProgressBar(activity)

        progressBar.isIndeterminate = true
        progressBar.indeterminateDrawable = ResourcesCompat.getDrawable(activity.resources, R.drawable.rotating_loader, null)
        activity.addContentView(progressBar, params)

        progressBar.scaleX = 0.8F
        progressBar.scaleY = 0.8F
        progressBar.x = ((activity.windowManager.defaultDisplay.width.toFloat() / 2) - (activity.resources.getDimension(R.dimen.loading_img_size) / 2))
        progressBar.y = ((activity.windowManager.defaultDisplay.height.toFloat() / 2) - (activity.resources.getDimension(R.dimen.loading_img_size) / 2))

        return progressBar
    }

}
