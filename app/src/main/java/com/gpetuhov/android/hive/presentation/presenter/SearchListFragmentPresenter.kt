package com.gpetuhov.android.hive.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.interactor.FavoritesInteractor
import com.gpetuhov.android.hive.domain.interactor.SearchInteractor
import com.gpetuhov.android.hive.domain.model.User
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.presentation.view.SearchListFragmentView
import com.gpetuhov.android.hive.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@InjectViewState
class SearchListFragmentPresenter :
    MvpPresenter<SearchListFragmentView>(),
    SearchInteractor.Callback,
    FavoritesInteractor.Callback {

    @Inject lateinit var repo: Repo

    var queryLatitude = Constants.Map.DEFAULT_LATITUDE
    var queryLongitude = Constants.Map.DEFAULT_LONGITUDE
    var queryRadius = Constants.Map.DEFAULT_RADIUS
    var queryText = ""

    var searchResultList = mutableListOf<User>()

    private val searchInteractor = SearchInteractor(this)
    private var favoritesInteractor = FavoritesInteractor(this)

    init {
        HiveApp.appComponent.inject(this)
    }

    // === SearchInteractor.Callback ===

    override fun onSearchComplete() = viewState.onSearchComplete()

    // === FavoritesInteractor.Callback ===

    override fun onFavoritesError(errorMessage: String) = viewState.showToast(errorMessage)

    // === Public methods ===

    fun navigateUp() = viewState.navigateUp()

    fun onResume() {
        repo.setSearchListActive(true)
        search()
    }

    fun onPause() {
        repo.stopGettingSearchResultUpdates()
        repo.setSearchListActive(false)
    }

    fun updateSearchResult(searchResult: MutableMap<String, User>) {
        sortSearchResultList(searchResult.values.toMutableList()) { sortedList ->
            searchResultList.clear()
            searchResultList.addAll(sortedList)
            viewState.updateUI()
        }
    }

    fun showDetails(userUid: String, offerUid: String) {
        // This is needed to get user details immediately from the already available search results
        repo.initSearchUserDetails(userUid)
        viewState.showDetails(offerUid)
    }

    fun favorite(isFavorite: Boolean, userUid: String, offerUid: String) =
        favoritesInteractor.favorite(isFavorite, userUid, offerUid)

    // === Private methods ===

    private fun search() {
        viewState.onSearchStart()
        searchInteractor.search(queryLatitude, queryLongitude, queryRadius, queryText)
    }

    private fun sortSearchResultList(unsortedList: MutableList<User>, onComplete: (MutableList<User>) -> Unit) {
        GlobalScope.launch {
            val sortedList = mutableListOf<User>()
            val userList = mutableListOf<User>()
            val offerList = mutableListOf<User>()

            // Separate users and offers into different lists
            unsortedList.forEach { if (it.offerSearchResultIndex == -1) userList.add(it) else offerList.add(it) }

            // TODO: sort users and offers by name

            // TODO: this should change according to user selected options
            sortedList.addAll(offerList)
            sortedList.addAll(userList)

            launch(Dispatchers.Main) { onComplete(sortedList) }
        }
    }
}