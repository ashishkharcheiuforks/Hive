package com.gpetuhov.android.hive.domain.interactor

import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.interactor.base.Interactor
import com.gpetuhov.android.hive.domain.model.Offer
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.domain.util.ResultMessages
import javax.inject.Inject

class SaveOfferInteractor(private val callback: Callback) : Interactor {

    interface Callback {
        fun onSaveOfferSuccess()
        fun onSaveOfferError(errorMessage: String)
    }

    @Inject lateinit var repo: Repo
    @Inject lateinit var resultMessages: ResultMessages

    private var offer: Offer? = null

    init {
        HiveApp.appComponent.inject(this)
    }

    // Do not call this directly!
    override fun execute() {
        // Count photos that are NOT marked as deleted
        val photoCount = offer?.photoList?.filter { !it.isDeleted }?.size ?: 0

        if (offer?.title == null || offer?.title == "") {
            callback.onSaveOfferError(resultMessages.getOfferEmptyTitleErrorMessage())

        } else if (offer?.description == null || offer?.description == "") {
            callback.onSaveOfferError(resultMessages.getOfferEmptyDescriptionErrorMessage())

        } else if (photoCount == 0) {
            callback.onSaveOfferError(resultMessages.getOfferEmptyPhotoListErrorMessage())

        } else {
            repo.saveOffer(
                offer,
                { callback.onSaveOfferSuccess() },
                { callback.onSaveOfferError(resultMessages.getSaveOfferErrorMessage()) }
            )
        }
    }

    // Call this method to save offer
    fun saveOffer(offer: Offer) {
        this.offer = offer
        execute()
    }
}