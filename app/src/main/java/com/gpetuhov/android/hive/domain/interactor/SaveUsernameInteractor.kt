package com.gpetuhov.android.hive.domain.interactor

import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.interactor.base.SaveUserPropertyInteractor

class SaveUsernameInteractor(private val callback: Callback) : SaveUserPropertyInteractor() {

    init {
        HiveApp.appComponent.inject(this)
    }

    // Do not call this directly, call save() instead!
    override fun execute() {
        repo.saveUserUsername(newValue) { callback.onSaveError(resultMessages.getSaveUsernameErrorMessage()) }
    }
}