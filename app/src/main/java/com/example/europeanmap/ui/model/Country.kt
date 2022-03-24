package com.example.europeanmap.ui.model

import com.google.android.gms.maps.model.LatLng

data class Country(
    val capital: String,
    val countryCode: String,
    val latLng: LatLng,
    val name: String,
    val timezones: List<String>,
    val distanceFromHome: Double? = null,
    val isHome: Boolean = false
)
