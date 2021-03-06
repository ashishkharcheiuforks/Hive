package com.gpetuhov.android.hive.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.databinding.FragmentFavoriteUsersBinding
import com.gpetuhov.android.hive.domain.model.User
import com.gpetuhov.android.hive.presentation.presenter.FavoriteUsersFragmentPresenter
import com.gpetuhov.android.hive.presentation.view.FavoriteUsersFragmentView
import com.gpetuhov.android.hive.ui.epoxy.user.favorite.controller.UserFavoriteListController
import com.gpetuhov.android.hive.ui.fragment.base.BaseFragment
import com.gpetuhov.android.hive.ui.viewmodel.FavoriteUsersViewModel
import com.pawegio.kandroid.toast

class FavoriteUsersFragment : BaseFragment(), FavoriteUsersFragmentView {

    @InjectPresenter lateinit var presenter: FavoriteUsersFragmentPresenter

    private var controller: UserFavoriteListController? = null
    private var binding: FragmentFavoriteUsersBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = UserFavoriteListController(presenter)
        controller?.onRestoreInstanceState(savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_users, container, false)

        val favoriteUsersRecyclerView = binding?.root?.findViewById<EpoxyRecyclerView>(R.id.favorite_users_recycler_view)
        favoriteUsersRecyclerView?.adapter = controller?.adapter

        val viewModel = ViewModelProviders.of(this).get(FavoriteUsersViewModel::class.java)

        // This is needed, because for some reason, there were multiple observers
        // when opening user details and going back for several times
        // (maybe because this fragment is inside the view pager of another fragment).
        viewModel.favoriteUsers.removeObservers(this)

        viewModel.favoriteUsers.observe(this, Observer<MutableList<User>> { favoriteUsersList ->
            binding?.favoriteUsersListEmpty = favoriteUsersList.isEmpty()
            controller?.changeFavoriteUsersList(favoriteUsersList)
        })

        return binding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controller?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    // === FavoriteUsersFragmentView ===

    override fun showUserDetails() {
        val action = FavoriteUsersFragmentDirections.actionGlobalUserDetailsFragment()
        findNavController().navigate(action)
    }

    override fun showOfferDetails(offerUid: String) {
        val action = FavoriteUsersFragmentDirections.actionGlobalOfferDetailsFragment(offerUid)
        findNavController().navigate(action)
    }

    override fun showToast(message: String) {
        toast(message)
    }
}