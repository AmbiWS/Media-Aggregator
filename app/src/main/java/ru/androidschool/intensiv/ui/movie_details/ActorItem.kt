package ru.androidschool.intensiv.ui.movie_details

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.*
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.extensions.ImageViewExtensions.loadImage

class ActorItem(
    private val content: Actor,
    private val onClick: (actor: Actor) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_actor

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.actorName.text = content.name
        viewHolder.content_actor.setOnClickListener {
            onClick.invoke(content)
        }

        viewHolder.actorImage.loadImage(BuildConfig.PHOTO_PATH + content.photoPath)
    }
}
