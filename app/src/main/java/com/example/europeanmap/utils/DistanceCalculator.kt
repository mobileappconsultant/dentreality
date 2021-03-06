package com.example.europeanmap.utils

import com.example.europeanmap.ui.model.Country
import com.example.europeanmap.utils.LatLngUtils.distanceBetween

class DistanceCalculator {
    private var visited = mutableListOf<Country>()

    fun getRoute(home: Country, destinations: List<Country>, start: Boolean = true): List<Country> {
        if (destinations.isEmpty())
            return visited
        if (start) visited = mutableListOf()
        val shortest = getShortestDistance(home, destinations)
        visited.add(shortest)
        val remainingDestinations = destinations.toMutableList()
        remainingDestinations.remove(shortest)
        getRoute(shortest, remainingDestinations, false)
        return visited
    }

    private fun getShortestDistance(current: Country, destinations: List<Country>): Country {
        val distanceBetween = mutableMapOf<Country, Double?>()
        destinations.forEach {
            distanceBetween[it] = current.latLng.distanceBetween(it.latLng)
        }
        val min = distanceBetween.minByOrNull {
            it.value ?: 0.0
        }
        return min?.key ?: current
    }
}
