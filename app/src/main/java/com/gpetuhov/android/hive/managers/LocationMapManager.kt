package com.gpetuhov.android.hive.managers

import com.google.android.gms.maps.GoogleMap
import com.gpetuhov.android.hive.util.Constants
import timber.log.Timber

class LocationMapManager {

    fun initMap(googleMap: GoogleMap) {
        try {
            // Show my location (blue point)
            googleMap.isMyLocationEnabled = true

        } catch (e: SecurityException) {
            Timber.tag("LocationFragment").d("Location permission not granted")
        }

        // Enable compass (will show on map rotate)
        googleMap.uiSettings.isCompassEnabled = true

        // Enable my location button
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        // Enable zoom buttons
        googleMap.uiSettings.isZoomControlsEnabled = true

        // Enable toolbar that opens Google Maps App
        googleMap.uiSettings.isMapToolbarEnabled = true

        // Set minimum and maximum zoom
        googleMap.setMinZoomPreference(Constants.Map.MIN_ZOOM)
        googleMap.setMaxZoomPreference(Constants.Map.MAX_ZOOM)
    }
}