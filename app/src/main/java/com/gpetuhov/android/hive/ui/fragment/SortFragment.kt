package com.gpetuhov.android.hive.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.epoxy.EpoxyRecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.databinding.FragmentSortBinding
import com.gpetuhov.android.hive.presentation.presenter.SortFragmentPresenter
import com.gpetuhov.android.hive.presentation.view.SortFragmentView
import com.gpetuhov.android.hive.ui.epoxy.sort.controller.SortListController
import com.gpetuhov.android.hive.ui.fragment.base.BaseFragment
import com.gpetuhov.android.hive.util.hideBottomNavigationView
import com.gpetuhov.android.hive.util.hideMainHeader
import com.gpetuhov.android.hive.util.setActivitySoftInputPan

class SortFragment : BaseFragment(), SortFragmentView {

    @InjectPresenter lateinit var presenter: SortFragmentPresenter

    private var controller: SortListController? = null
    private var binding: FragmentSortBinding? = null

    private var clearSortDialog: MaterialDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Adjust_pan is needed to prevent activity from being pushed up by the keyboard
        setActivitySoftInputPan()

        hideMainHeader()
        hideBottomNavigationView()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort, container, false)
        binding?.presenter = presenter

        controller = SortListController(presenter)
        controller?.onRestoreInstanceState(savedInstanceState)

        val filterRecyclerView = binding?.root?.findViewById<EpoxyRecyclerView>(R.id.sort_recycler_view)
        filterRecyclerView?.adapter = controller?.adapter

        presenter.init()

        updateUI()

        return binding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        controller?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // This is needed to prevent memory leaks.
        // Here we intentially dismiss dialogs directly, not via the presenter,
        // so that Moxy command queue doesn't change.
        dismissDialogs()
    }

    // === SortFragmentView ===

    override fun showClearSortDialog() {
        initClearSortDialog()
        clearSortDialog?.show() ?: Unit
    }

    override fun dismissClearSortDialog() = clearSortDialog?.dismiss() ?: Unit

    override fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun updateUI() = controller?.requestModelBuild() ?: Unit

    // === Private methods ===

    private fun initClearSortDialog() {
        if (context != null) {
            clearSortDialog = MaterialDialog(context!!)
                .title(R.string.clear_sort)
                .message(R.string.clear_sort_prompt)
                .noAutoDismiss()
                .cancelable(false)
                .positiveButton(R.string.yes) { presenter.clearSort() }
                .negativeButton(R.string.no) { presenter.dismissClearSortDialog() }
        }
    }

    private fun dismissDialogs() = dismissClearSortDialog()
}