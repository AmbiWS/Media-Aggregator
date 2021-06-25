package ru.androidschool.intensiv.ui.feed

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDBContent
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage

class MovieItem(
    private val content: MovieDBContent,
    private val onClick: (movie: MovieDBContent) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }

        viewHolder.image_preview.loadImage("https://www.themoviedb.org/t/p/w220_and_h330_face/" + content.posterPath)
    }
}
