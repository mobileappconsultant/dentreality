package com.example.europeanmap.utils

import com.example.europeanmap.ui.model.Country
import com.example.europeanmap.usecases.model.CountryDomain
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class CountryMapper @Inject constructor() {
    fun mapToPresentation(domain: CountryDomain) = Country(
        capital = domain.capital,
        countryCode = domain.countryCode,
        name = domain.name,
        latLng = LatLng(domain.latLng.first(), domain.latLng[1]),
        timezones = domain.timezones
    )
}
