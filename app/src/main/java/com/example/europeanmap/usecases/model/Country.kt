package com.example.europeanmap.usecases.model


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("capital")
    val capital: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("latlng")
    val latlng: List<Double>,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezones")
    val timezones: List<String>
)