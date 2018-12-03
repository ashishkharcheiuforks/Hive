package com.gpetuhov.android.hive.ui.fragment

import com.gpetuhov.android.hive.util.moxy.MvpAppCompatFragment

// This class is needed to handle back pressed inside fragments
open class BaseFragment : MvpAppCompatFragment() {

    // Return true if back button click is handled by fragment
    fun onBackPressed() = false
}