package com.example.europeanmap.utils

import com.google.android.gms.maps.model.LatLng
import java.math.RoundingMode
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object LatLngUtils {
    private const val EARTH_RADIUS_KM: Double = 6372.8

    /**
     * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
     * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
     * sides and angles of spherical "triangles".
     *
     * https://rosettacode.org/wiki/Haversine_formula#Java
     *
     * @return Distance in kilometers
     */
    fun LatLng.distanceBetween(destination: LatLng?): Double? {
        destination?.let {
            val dLat = Math.toRadians(destination.latitude - this.latitude)
            val dLon = Math.toRadians(destination.longitude - this.longitude)
            val originLat = Math.toRadians(this.latitude)
            val destinationLat = Math.toRadians(destination.latitude)

            val a = sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) * cos(originLat) * cos(destinationLat)
            val c = 2 * asin(sqrt(a))
            return (EARTH_RADIUS_KM * c).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        }
        return null
    }
}
