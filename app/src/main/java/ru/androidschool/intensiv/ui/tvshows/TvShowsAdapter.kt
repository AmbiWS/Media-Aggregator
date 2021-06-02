package ru.androidschool.intensiv.ui.tvshows

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TvShowsAdapter(
    fragment: Fragment,
    private val itemsCount: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return TvShowsFragment.newInstance()
    }

}
