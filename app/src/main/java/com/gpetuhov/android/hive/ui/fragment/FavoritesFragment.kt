package com.gpetuhov.android.hive.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.presentation.presenter.FavoritesFragmentPresenter
import com.gpetuhov.android.hive.presentation.view.FavoritesFragmentView
import com.gpetuhov.android.hive.ui.fragment.base.BaseFragment
import com.gpetuhov.android.hive.util.hideMainHeader
import com.gpetuhov.android.hive.util.setActivitySoftInputPan
import com.gpetuhov.android.hive.util.showBottomNavigationView
import com.gpetuhov.android.hive.ui.adapter.FavoritesAdapter
import com.gpetuhov.android.hive.util.setVisible
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment(), FavoritesFragmentView {

    @InjectPresenter lateinit var presenter: FavoritesFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Adjust_pan is needed to prevent activity from being pushed up by the keyboard
        setActivitySoftInputPan()

        hideMainHeader()
        showBottomNavigationView()

        presenter.refreshFavorites()

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // We must use CHILD FragmentManager here,
        // so that fragments in the view pager are destroyed
        // when parent fragment is destroyed.
        val adapter = FavoritesAdapter(context, childFragmentManager)
        favorites_viewpager.adapter = adapter
        favorites_viewpager.scrollable = false
        favorites_tabs.setupWithViewPager(favorites_viewpager)
    }

    // === FavoritesFragmentView ===

    override fun showProgress() = progressVisible(true)

    override fun hideProgress() = progressVisible(false)

    // === Private methods ===

    private fun progressVisible(isVisible: Boolean) = favorites_load_progress.setVisible(isVisible)
}