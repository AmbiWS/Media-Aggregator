package ru.androidschool.intensiv.ui.tvshows

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_tv_shows.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage

class TvShowsItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_tv_shows

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_tv_shows.text = content.title
        viewHolder.tv_shows_rating.rating = content.rating
        viewHolder.content_tv_show.setOnClickListener {
            onClick.invoke(content)
        }

        // TODO Получать из модели
        viewHolder.image_tv_shows.loadImage("https://assets.vogue.in/photos/5fb498ce49cee77f06f7e19f/16:9/w_2400,h_1350,c_limit/The-Queens-Gambit-vogue-171120-courtesy-Netflix-4.jpg")
    }
}
