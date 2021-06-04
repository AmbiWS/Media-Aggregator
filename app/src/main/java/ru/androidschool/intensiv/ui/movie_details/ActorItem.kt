package ru.androidschool.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.*
import kotlinx.android.synthetic.main.item_tv_shows.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor

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
        Picasso.get()
            .load("https://assets.vogue.in/photos/5fb498ce49cee77f06f7e19f/16:9/w_2400,h_1350,c_limit/The-Queens-Gambit-vogue-171120-courtesy-Netflix-4.jpg")
            .into(viewHolder.actorImage)
    }

}