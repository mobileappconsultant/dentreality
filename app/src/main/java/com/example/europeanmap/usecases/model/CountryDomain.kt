package com.example.europeanmap.usecases.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class CountryDomain(
    @SerializedName("capital")
    val capital: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("latlng")
    val latLng: List<Double>,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezones")
    val timezones: List<String>
) {
    fun getLatLng(): LatLng {
        return LatLng(latLng.first(), latLng[1])
    }
}
