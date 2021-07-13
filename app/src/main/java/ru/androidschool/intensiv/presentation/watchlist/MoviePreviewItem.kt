package ru.androidschool.intensiv.presentation.watchlist

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.dto.MovieContent
import ru.androidschool.intensiv.domain.extensions.ImageViewExtensions.loadImage

class MoviePreviewItem(
    private val content: MovieContent,
    private val onClick: (movie: MovieContent) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_small

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.image_preview.setOnClickListener {
            onClick.invoke(content)
        }

        content.posterPath?.let { viewHolder.image_preview.loadImage(it) }
    }
}
