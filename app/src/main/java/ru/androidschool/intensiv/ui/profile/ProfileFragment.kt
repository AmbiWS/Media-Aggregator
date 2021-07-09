package ru.androidschool.intensiv.ui.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieContent
import ru.androidschool.intensiv.extensions.ObservableExtensions.subscribeIoObserveMT
import ru.androidschool.intensiv.room.MovieDB
import ru.androidschool.intensiv.ui.watchlist.MoviePreviewItem
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileTabLayoutTitles: Array<String>
    private val FAVORITE_MOVIES_INDEX = 0
    private val MOVIES_TITLE_START_INDEX = 2

    private val mDisposable = CompositeDisposable()

    private var profilePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(
                requireContext(),
                "Selected position: $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get()
            .load(R.drawable.ic_avatar)
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.ic_avatar)
            .into(avatar)

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)

        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        doppelgangerViewPager.adapter = profileAdapter

        doppelgangerViewPager.registerOnPageChangeCallback(profilePageChangeCallback)

        val movieDao = MovieDB.getInstance(requireContext())?.movieDao()

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->

            // Выделение первой части заголовка таба
            // Название таба
            val title = profileTabLayoutTitles[position]
            // Раздеряем название на части. Первый элемент будет кол-во
            val parts = profileTabLayoutTitles[position].split(" ")
            val number: String

            if (position == FAVORITE_MOVIES_INDEX) {

                mDisposable.add(movieDao?.getAll()
                    ?.subscribeIoObserveMT()
                    ?.subscribe {

                        val movieContentList = it.map {
                            i -> MovieContent(i.title, 0.0, i.posterPath, i.id)
                        }

                        val moviesCount = movieContentList.map {
                            MoviePreviewItem(
                                it
                            ) { movie -> }
                        }.toList().size

                        val tabTitle = tabLayout.getTabAt(FAVORITE_MOVIES_INDEX)?.text?.substring(MOVIES_TITLE_START_INDEX)
                        val tabText = moviesCount.toString() + "\n" + tabTitle?.substring(1)
                        Timber.d(tabTitle.toString())

                        tabLayout.getTabAt(FAVORITE_MOVIES_INDEX)?.let { it1 ->
                            loadData(tabText, moviesCount.toString(),
                                it1
                            )
                        }

                        mDisposable.clear()
                    })

            }

            number = parts[0]
            loadData(title, number, tab)

        }.attach()
    }

    private fun loadData(text: String, number: String, tab: TabLayout.Tab) {
        val spannableStringTitle = SpannableString(text)
        spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number.count(), 0)
        tab.text = spannableStringTitle
    }
}
