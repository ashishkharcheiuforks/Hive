package com.gpetuhov.android.hive.presentation.presenter

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.maps.GoogleMap
import com.gpetuhov.android.hive.application.HiveApp
import com.gpetuhov.android.hive.domain.interactor.SearchInteractor
import com.gpetuhov.android.hive.domain.model.User
import com.gpetuhov.android.hive.domain.repository.Repo
import com.gpetuhov.android.hive.managers.MapManager
import com.gpetuhov.android.hive.presentation.view.MapFragmentView
import com.gpetuhov.android.hive.util.Constants
import com.gpetuhov.android.hive.util.Settings
import javax.inject.Inject

@InjectViewState
class MapFragmentPresenter :
    MvpPresenter<MapFragmentView>(),
    MapManager.Callback,
    SearchInteractor.Callback {

    @Inject lateinit var mapManager: MapManager
    @Inject lateinit var repo: Repo
    @Inject lateinit var settings: Settings

    // Current query text from search EditText
    // (binded to view with two-way data binding).
    var queryText = ""

    private var queryLatitude = Constants.Map.DEFAULT_LATITUDE
    private var queryLongitude = Constants.Map.DEFAULT_LONGITUDE
    private var queryRadius = Constants.Map.DEFAULT_RADIUS

    private val searchInteractor = SearchInteractor(this)

    init {
        HiveApp.appComponent.inject(this)
    }

    // === MapManager.Callback ===

    override fun onMinZoom() = viewState.onMinZoom()

    override fun onMaxZoom() = viewState.onMaxZoom()

    override fun onNormalZoom() = viewState.onNormalZoom()

    override fun onCameraIdle(latitude: Double, longitude: Double, radius: Double) {
        queryLatitude = latitude
        queryLongitude = longitude
        queryRadius = radius
        search()
    }

    override fun showDetails(userUid: String, offerUid: String) {
        // This is needed to get user details immediately from the already available search results
        repo.initSearchUserDetails(userUid)
        viewState.showDetails(offerUid)
    }

    // === SearchInteractor.Callback ===

    override fun onSearchComplete() = viewState.onSearchComplete()

    // === Public methods ===

    fun initSearchQueryText() {
        if (queryText == "") {
            queryText = settings.getSearchQueryText()
        }
    }

    fun initMap(map: GoogleMap) = mapManager.initMap(this, map)

    fun updateMarkers(searchResult: MutableMap<String, User>) = mapManager.updateMarkers(searchResult)

    fun search() {
        viewState.onSearchStart()
        viewState.hideKeyboard()
        settings.setSearchQueryText(queryText)
        searchInteractor.search(queryLatitude, queryLongitude, queryRadius, queryText)
    }

    fun cancelSearch() {
        settings.resetSearchFilter()
        settings.resetSearchSort()
        viewState.clearSearch()
        viewState.updateFilterButton()
        search()
    }

    fun moveToCurrentLocation() = mapManager.moveToCurrentLocation()

    fun zoomIn() = mapManager.zoomIn()

    fun zoomOut() = mapManager.zoomOut()

    fun showList() {
        viewState.showList(queryLatitude, queryLongitude, queryRadius)
        viewState.clearSearch()
    }

    fun showFilter() = viewState.showFilter()

    fun filterIsDefault() = settings.getSearchFilter().isDefault

    // === Lifecycle calls ===

    fun onCreateView(savedInstanceState: Bundle?) {
        // Restore map state and restore saved query text
        mapManager.restoreMapState(savedInstanceState)
    }

    fun onPause() {
        // Save map state here, because onPause() is guaranteed to be called
        mapManager.saveMapState()
        mapManager.clearMarkers()
        repo.stopGettingSearchResultUpdates()
    }

    fun onSaveInstanceState(outState: Bundle) = mapManager.saveOutState(outState)
}