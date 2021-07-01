package ru.androidschool.intensiv.ui.tvshows

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_tv_shows.*
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieContent
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage

class TvShowsItem(
    private val content: MovieContent,
    private val onClick: (tvShow: MovieContent) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_tv_shows

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_tv_shows.text = content.title
        viewHolder.tv_shows_rating.rating = content.rating
        viewHolder.content_tv_show.setOnClickListener {
            onClick.invoke(content)
        }

        viewHolder.image_tv_shows.loadImage(BuildConfig.POSTER_PATH + content.posterPath)
    }
}
