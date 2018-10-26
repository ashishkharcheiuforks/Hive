package com.gpetuhov.android.hive.presentation.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.managers.AuthManager
import com.gpetuhov.android.hive.presentation.view.ProfileFragmentView
import com.gpetuhov.android.hive.repository.Repository
import com.gpetuhov.android.hive.util.isOnline
import timber.log.Timber
import javax.inject.Inject

// This is the presenter for ProfileFragment
// ALL (!!!) user interactions must be performed through this presenter ONLY!
@InjectViewState
class ProfileFragmentPresenter : MvpPresenter<ProfileFragmentView>() {

    companion object {
        private const val TAG = "ProfileFragPresenter"
    }

    @Inject lateinit var repo: Repository
    @Inject lateinit var authManager: AuthManager

    // Keeps current text entered in username dialog
    private var tempUsername = ""

    init {
        HiveApp.appComponent.inject(this)
    }

    // We must call ProfileFragmentView's methods not directly, but through ViewState only.
    // This way Moxy will remember current state of the view and will restore it,
    // when the view is recreated.
    fun showSignOutDialog() {
        viewState.disableSignOutButton()
        viewState.showSignOutDialog()
    }

    fun signOut(context: Context?) {
        viewState.dismissSignOutDialog()

        // Try to sign out if online only
        if (isOnline(context)) {
            authManager.signOut(
                context,
                { viewState.enableSignOutButton() },
                {
                    viewState.onSignOutError()
                    viewState.enableSignOutButton()
                }
            )
        } else {
            viewState.onSignOutNetworkError()
            viewState.enableSignOutButton()
        }
    }

    fun signOutCancel() {
        viewState.dismissSignOutDialog()
        viewState.enableSignOutButton()
    }

    fun showDeleteUserDialog() = viewState.showDeleteUserDialog()

    fun deleteUser(context: Context?) {
        dismissDeleteUserDialog()

        // Try to delete account if online only
        if (isOnline(context)) {
            authManager.deleteAccount(
                context,
                { viewState.onDeleteUserSuccess() },
                { viewState.onDeleteUserError() }
            )
        } else {
            viewState.onDeleteUserNetworkError()
        }
    }

    fun dismissDeleteUserDialog() = viewState.dismissDeleteUserDialog()

    fun showUsernameDialog() = viewState.showUsernameDialog()

    fun getPrefill(): String {
        // Prefill dialog with currently entered text or current username
        val username = repo.currentUser.value?.username ?: ""
        return if (tempUsername != "") tempUsername else username
    }

    fun updateTempUsername(newTempUsername: String) {
        Timber.tag(TAG).d("TempUsername = $newTempUsername")
        tempUsername = newTempUsername
    }

    fun saveUsername() {
        Timber.tag(TAG).d("Username = $tempUsername")
        repo.updateUserUsername(tempUsername, { /* Do nothing */ }, { viewState.onSaveUsernameError() })
        dismissUsernameDialog()
    }

    fun dismissUsernameDialog() {
        tempUsername = ""
        viewState.dismissUsernameDialog()
    }
}