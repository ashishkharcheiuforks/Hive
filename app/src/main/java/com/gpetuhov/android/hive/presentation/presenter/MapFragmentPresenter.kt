package com.gpetuhov.android.hive.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.presentation.view.MapFragmentView
import javax.inject.Inject

@InjectViewState
class MapFragmentPresenter : MvpPresenter<MapFragmentView>() {

    @Inject lateinit var repo: Repo

    init {
        HiveApp.appComponent.inject(this)
    }
}