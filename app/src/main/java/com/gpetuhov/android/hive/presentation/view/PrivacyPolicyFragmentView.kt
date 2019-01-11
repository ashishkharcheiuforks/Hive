package com.gpetuhov.android.hive.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface PrivacyPolicyFragmentView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun navigateUp()
}