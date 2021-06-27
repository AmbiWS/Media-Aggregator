package ru.androidschool.intensiv.ui.search

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_search.*
import ru.androidschool.intensiv.R

class SearchItem(
    private val content: String
) : Item() {

    override fun getLayout() = R.layout.item_search

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewSearchItem.text = content
    }
}