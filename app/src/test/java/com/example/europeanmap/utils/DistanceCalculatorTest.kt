package com.example.europeanmap.utils

import com.example.europeanmap.ui.model.Country
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DistanceCalculatorTest {
    lateinit var sut: DistanceCalculator

    private val home = Country(
        capital = "Pars",
        name = "France",
        latLng = LatLng(46.0, 2.0),
        countryCode = "",
        timezones = listOf()
    )
    private val germany = Country(
        capital = "Berlin",
        name = "Germany",
        latLng = LatLng(
            51.0,
            9.0
        ),
        countryCode = "",
        timezones = listOf()
    )
    private val norway = Country(
        capital = "Oslo",
        name = "Norway",
        latLng = LatLng(
            62.0,
            10.0
        ),
        countryCode = "",
        timezones = listOf()
    )
    private val madrid = Country(
        capital = "Madrid",
        name = "Spain",
        latLng = LatLng(
            40.0,
            -4.0
        ),
        countryCode = "",
        timezones = listOf()
    )
    private val portugal = Country(
        capital = "Lisbon",
        name = "Portugal",
        latLng = LatLng(
            39.5,
            -8.0
        ),
        countryCode = "",
        timezones = listOf()
    )

    private val destinations = listOf(
        germany,
        madrid,
        portugal,
        norway
    )

    @Before
    fun setUp() {
        sut = DistanceCalculator()
    }

    @Test
    fun shouldWorkCorrectly() {
        val actual = sut.getRoute(home, destinations)
        val expected = listOf(
            germany,
            norway,
            madrid,
            portugal
        )
        Assert.assertEquals(expected.first(), actual.first())
        Assert.assertEquals(expected[1], actual[1])
        Assert.assertEquals(expected[2], actual[2])
        Assert.assertEquals(expected[3], actual[3])
    }
}
