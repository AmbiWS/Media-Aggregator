package ru.androidschool.intensiv.ui

import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import ru.androidschool.intensiv.R

object LoadingImageView {

    private val params : ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    fun getLoadingImage(activity: FragmentActivity): ImageView {

        val imageView = ImageView(activity)

        imageView.setImageResource(R.drawable.loading)
        activity.addContentView(imageView, params)

        imageView.maxWidth = 64
        imageView.maxHeight = 64
        imageView.x = ((activity.windowManager.defaultDisplay.width.toFloat() / 2) - (activity.resources.getDimension(
            R.dimen.loading_img_size
        ) / 2))
        imageView.y = ((activity.windowManager.defaultDisplay.height.toFloat() / 2) - (activity.resources.getDimension(
            R.dimen.loading_img_size
        ) / 2))

        return imageView

    }

    fun getRotateStyle(): RotateAnimation {

        val rotate = RotateAnimation(
            0F, 360F,
            Animation.RELATIVE_TO_PARENT, 0.5f,
            Animation.RELATIVE_TO_PARENT, 0.5f
        )

        rotate.duration = 900
        rotate.repeatCount = Animation.INFINITE

        return rotate

    }
}