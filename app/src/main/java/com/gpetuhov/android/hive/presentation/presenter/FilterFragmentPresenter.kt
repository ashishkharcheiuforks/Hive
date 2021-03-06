package com.gpetuhov.android.hive.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.model.Filter
import com.gpetuhov.android.hive.presentation.view.FilterFragmentView
import com.gpetuhov.android.hive.util.Settings
import javax.inject.Inject

@InjectViewState
class FilterFragmentPresenter : MvpPresenter<FilterFragmentView>() {

    @Inject lateinit var settings: Settings

    private var filter = Filter()
    private var isFilterChanged = false

    init {
        HiveApp.appComponent.inject(this)
    }

    // === Public methods ===

    // --- Init presenter ---

    fun init() {
        // This check is needed to prevent overwriting changed filter
        // with filter from settings on screen rotation.
        if (!isFilterChanged) filter = settings.getSearchFilter()
    }

    // --- Basic filter params ---

    fun showUsersOffersAll() {
        setFilterChanged()
        filter.setShowUsersOffersAll()
    }

    fun isShowUsersOffersAll() = filter.isShowUsersOffersAll

    fun showUsersOnly() {
        setFilterChanged()
        filter.setShowUsersOnly()
    }

    fun isShowUsersOnly() = filter.isShowUsersOnly

    fun showOffersOnly() {
        setFilterChanged()
        filter.setShowOffersOnly()
    }

    fun isShowOffersOnly() = filter.isShowOffersOnly

    // --- Offer filter params ---

    fun freeOffersOnly(value: Boolean) {
        setFilterChanged()
        filter.isFreeOffersOnly = value
    }

    fun isFreeOffersOnly() = filter.isFreeOffersOnly

    fun offersWithReviewsOnly(value: Boolean) {
        setFilterChanged()
        filter.isOffersWithReviewsOnly = value
    }

    fun isOffersWithReviewsOnly() = filter.isOffersWithReviewsOnly

    // --- Filter contacts params ---

    fun setHasPhone(value: Boolean) {
        setFilterChanged()
        filter.hasPhone = value
    }

    fun hasPhone() = filter.hasPhone

    fun setHasEmail(value: Boolean) {
        setFilterChanged()
        filter.hasEmail = value
    }

    fun hasEmail() = filter.hasEmail

    fun setHasSkype(value: Boolean) {
        setFilterChanged()
        filter.hasSkype = value
    }

    fun hasSkype() = filter.hasSkype

    fun setHasFacebook(value: Boolean) {
        setFilterChanged()
        filter.hasFacebook = value
    }

    fun hasFacebook() = filter.hasFacebook

    fun setHasTwitter(value: Boolean) {
        setFilterChanged()
        filter.hasTwitter = value
    }

    fun hasTwitter() = filter.hasTwitter

    fun setHasInstagram(value: Boolean) {
        setFilterChanged()
        filter.hasInstagram = value
    }

    fun hasInstagram() = filter.hasInstagram

    fun setHasYoutube(value: Boolean) {
        setFilterChanged()
        filter.hasYoutube = value
    }

    fun hasYoutube() = filter.hasYoutube

    fun setHasWebsite(value: Boolean) {
        setFilterChanged()
        filter.hasWebsite = value
    }

    fun hasWebsite() = filter.hasWebsite

    // --- Filter awards params ---

    fun setHasSuperProvider(value: Boolean) {
        setFilterChanged()
        filter.hasSuperProvider = value
    }

    fun hasSuperProvider() = filter.hasSuperProvider

    fun setHasGoodProvider(value: Boolean) {
        setFilterChanged()
        filter.hasGoodProvider = value
    }

    fun hasGoodProvider() = filter.hasGoodProvider

    fun setHasRockStar(value: Boolean) {
        setFilterChanged()
        filter.hasRockStar = value
    }

    fun hasRockStar() = filter.hasRockStar

    fun setHasAdorableProvider(value: Boolean) {
        setFilterChanged()
        filter.hasAdorableProvider = value
    }

    fun hasAdorableProvider() = filter.hasAdorableProvider

    fun setHasFavoriteProvider(value: Boolean) {
        setFilterChanged()
        filter.hasFavoriteProvider = value
    }

    fun hasFavoriteProvider() = filter.hasFavoriteProvider

    fun setHasTextMaster(value: Boolean) {
        setFilterChanged()
        filter.hasTextMaster = value
    }

    fun hasTextMaster() = filter.hasTextMaster

    fun setHasNewbie(value: Boolean) {
        setFilterChanged()
        filter.hasNewbie = value
    }

    fun hasNewbie() = filter.hasNewbie

    // --- Filter activity params ---

    fun activityAny() {
        setFilterChanged()
        filter.setActivityAny()
    }

    fun isActivityAny() = filter.isActivityAny

    fun activityStill() {
        setFilterChanged()
        filter.setActivityStill()
    }

    fun isActivityStill() = filter.isActivityStill

    fun activityWalking() {
        setFilterChanged()
        filter.setActivityWalking()
    }

    fun isActivityWalking() = filter.isActivityWalking

    fun activityRunning() {
        setFilterChanged()
        filter.setActivityRunning()
    }

    fun isActivityRunning() = filter.isActivityRunning

    fun activityBicycle() {
        setFilterChanged()
        filter.setActivityBicycle()
    }

    fun isActivityBicycle() = filter.isActivityBicycle

    fun activityVehicle() {
        setFilterChanged()
        filter.setActivityVehicle()
    }

    fun isActivityVehicle() = filter.isActivityVehicle

    // --- Clear filter ---

    fun showClearFilterDialog() = viewState.showClearFilterDialog()

    fun clearFilter() {
        setFilterChanged()
        filter = Filter()
        viewState.updateUI()
        dismissClearFilterDialog()
    }

    fun dismissClearFilterDialog() = viewState.dismissClearFilterDialog()

    // --- Show results ---

    fun showResult() {
        settings.setSearchFilter(filter)
        navigateUp()
    }

    // --- Navigation ---

    fun navigateUp() = viewState.navigateUp()

    // === Private methods ===

    private fun setFilterChanged() {
        isFilterChanged = true
    }
}