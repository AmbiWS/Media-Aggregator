package ru.androidschool.intensiv.ui.movie_details

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.data.ExtensionMethods.loadImage

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

        // TODO Получать из модели
        viewHolder.actorImage.loadImage("https://ca-times.brightspotcdn.com/dims4/default/f4e021e/2147483647/strip/true/crop/3000x2251+0+0/resize/840x630!/quality/90/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F3f%2Fa0%2F27ad67f17d56801f88f223048a4c%2F270ece9466a946488bf8149e39598616")
    }
}
