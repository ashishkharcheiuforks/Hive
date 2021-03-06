package com.gpetuhov.android.hive.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.model.Review
import com.gpetuhov.android.hive.domain.model.User
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.presentation.view.ReviewsAllFragmentView
import javax.inject.Inject

@InjectViewState
class ReviewsAllFragmentPresenter : MvpPresenter<ReviewsAllFragmentView>() {

    @Inject lateinit var repo: Repo

    var allReviewCount = 0
    var allRating = 0.0F
    var allReviews = mutableListOf<Review>()
    var isCurrentUser = false

    private var hasData = false

    init {
        HiveApp.appComponent.inject(this)
    }

    // === Public methods ===

    fun getAllReviews() {
        // This is needed to prevent loading reviews on every screen rotate
        if (!hasData) {
            hasData = true

            viewState.showProgress()

            val user = if (isCurrentUser) repo.currentUser().value else repo.secondUser().value
            calculateRating(user)

            // Notice, that we just load all user reviews WITHOUT listening to updates.
            repo.getAllUserReviews(isCurrentUser) { reviewList ->
                allReviews = reviewList
                viewState.hideProgress()
                viewState.updateUI()
            }
        }
    }

    fun navigateUp() = viewState.navigateUp()

    fun showOffer(offerUid: String) = if (isCurrentUser) viewState.updateOffer(offerUid) else viewState.openOffer(offerUid)

    // === Private methods ===

    // We calculate rating based on user.offerList instead of all reviews,
    // because the result is slightly different,
    // and we need it to be the same as in user details.
    private fun calculateRating(user: User?) {
        allRating = user?.averageRating ?: 0.0F
        allReviewCount = user?.totalReviewsCount ?: 0
    }
}