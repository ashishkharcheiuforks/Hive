package com.gpetuhov.android.hive.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gpetuhov.android.hive.R
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.databinding.FragmentProfileBinding
import com.gpetuhov.android.hive.managers.AuthManager
import com.gpetuhov.android.hive.ui.viewmodel.CurrentUserViewModel
import com.gpetuhov.android.hive.model.User
import com.gpetuhov.android.hive.presentation.presenter.ProfileFragmentPresenter
import com.gpetuhov.android.hive.presentation.view.ProfileFragmentView
import com.gpetuhov.android.hive.util.moxy.MvpAppCompatFragment
import com.gpetuhov.android.hive.repository.Repository
import com.gpetuhov.android.hive.util.isOnline
import com.pawegio.kandroid.toast
import timber.log.Timber
import javax.inject.Inject

class ProfileFragment : MvpAppCompatFragment(), ProfileFragmentView {

    companion object {
        private const val TAG = "ProfileFragment"
        private const val TEMP_USERNAME_KEY = "tempUsername"
    }

    @Inject lateinit var authManager: AuthManager
    @Inject lateinit var repo: Repository

    @InjectPresenter lateinit var presenter: ProfileFragmentPresenter

    private var usernameDialog: MaterialDialog? = null
    private var signOutDialog: MaterialDialog? = null
    private var deleteUserDialog: MaterialDialog? = null
    private var binding: FragmentProfileBinding? = null

    // Keeps current text entered in username dialog
    private var tempUsername = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        HiveApp.appComponent.inject(this)

        initUsernameDialog()
        initSignOutDialog()
        initDeleteUserDialog()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding?.handler = this

        val viewModel = ViewModelProviders.of(this).get(CurrentUserViewModel::class.java)

        // Every time current user data changes, update binding object with new data
        viewModel.currentUser.observe(this, Observer<User> { user ->
            binding?.user = user
        })

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            tempUsername = savedInstanceState.getString(TEMP_USERNAME_KEY, "")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(TEMP_USERNAME_KEY, tempUsername)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // This is needed to prevent memory leaks
        dismissUsernameDialog()
        dismissSignOutDialog()
        dismissDeleteUserDialog()
    }

    // === ProfileFragmentView ===

    override fun showSignOutDialog() {
        signOutDialog?.show()
    }

    override fun dismissSignOutDialog() {
        signOutDialog?.dismiss()
    }

    override fun showDeleteUserDialog() {
        deleteUserDialog?.show()
    }

    override fun dismissDeleteUserDialog() {
        deleteUserDialog?.dismiss()
    }

    override fun showUsernameDialog() {
        // Prefill dialog with currently entered text or current username
        val prefill = if (tempUsername != "") tempUsername else repo.currentUser.value?.username
        val editText = usernameDialog?.getInputField()
        editText?.setText(prefill)
        editText?.setSelection(editText.text.length)
        usernameDialog?.show()
    }

    override fun dismissUsernameDialog() {
        usernameDialog?.dismiss()
    }

    // === Public methods ===

    fun onSignOutButtonClick() {
        if (isOnline(context)) {
            presenter.showSignOutDialog()
        } else {
            toast(R.string.sign_out_no_network)
        }
    }

    fun onDeleteAccountButtonClick() {
        if (isOnline(context)) {
            presenter.showDeleteUserDialog()
        } else {
            toast(R.string.delete_account_no_network)
        }
    }

    fun onUsernameClick() {
        presenter.showUsernameDialog()
    }

    // === Private methods ===

    private fun initUsernameDialog() {
        if (context != null) {
            usernameDialog = MaterialDialog(context!!)
                .title(R.string.username)
                .noAutoDismiss()
                .cancelable(false)
                .input(hintRes = R.string.enter_username, waitForPositiveButton = false) { dialog, text ->
                    tempUsername = text.toString()
                }
                .positiveButton {
                    presenter.dismissUsernameDialog()
                    saveUsername(tempUsername)
                    tempUsername = ""
                }
                .negativeButton {
                    presenter.dismissUsernameDialog()
                    tempUsername = ""
                }
        }
    }

    private fun saveUsername(username: String) {
        Timber.tag(TAG).d("Username = $username")
        repo.updateUserUsername(username, { /* Do nothing */ }, { toast(R.string.username_save_error) })
    }

    private fun initSignOutDialog() {
        if (context != null) {
            signOutDialog = MaterialDialog(context!!)
                .title(R.string.sign_out)
                .message(R.string.prompt_sign_out)
                .noAutoDismiss()
                .cancelable(false)
                .positiveButton {
                    presenter.dismissSignOutDialog()
                    signOut()
                }
                .negativeButton {
                    presenter.dismissSignOutDialog()
                }
        }
    }

    private fun initDeleteUserDialog() {
        if (context != null) {
            deleteUserDialog = MaterialDialog(context!!)
                .title(R.string.delete_account)
                .message(R.string.prompt_delete_account)
                .noAutoDismiss()
                .cancelable(false)
                .positiveButton {
                    presenter.dismissDeleteUserDialog()
                    deleteAccount()
                }
                .negativeButton {
                    presenter.dismissDeleteUserDialog()
                }
        }
    }

    private fun signOut() {
        authManager.signOut(context) { toast(R.string.sign_out_error) }
    }

    private fun deleteAccount() {
        authManager.deleteAccount(
            context,
            { toast(R.string.delete_account_success) },
            { toast(R.string.delete_account_error) }
        )
    }
}