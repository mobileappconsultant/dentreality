package com.example.europeanmap.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europeanmap.ui.model.Country
import com.example.europeanmap.usecases.GetLocationsFromDeviceUseCase
import com.example.europeanmap.utils.CountryMapper
import com.example.europeanmap.utils.LatLngUtils.distanceBetween
import com.example.europeanmap.utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocationsFromDeviceUseCase: GetLocationsFromDeviceUseCase,
    private val countryMapper: CountryMapper,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    private val _locations = MutableStateFlow<List<Country>?>(null)
    val locations = _locations.asStateFlow()

    private val _selectedLocation = MutableStateFlow<Country?>(null)
    val selectedLocation = _selectedLocation.asStateFlow()

    init {
        getLocationsFromAssets()
    }

    private fun getLocationsFromAssets() {
        viewModelScope.launch {
            val data = getLocationsFromDeviceUseCase.execute()
            val home = sharedPrefs.getHomeCountry()
            _locations.value = data?.map {
                countryMapper.mapToPresentation(it).copy(
                    isHome = home?.name == it.name
                )
            }
        }
    }

    fun setHome(country: Country) {
        sharedPrefs.saveHome(country)
        getLocationsFromAssets()
        _selectedLocation.value = country.copy(distanceFromHome = null, isHome = true)
    }

    fun selectLocation(country: Country?) {
        val home = sharedPrefs.getHomeCountry()
        val isHome = home?.name == country?.name
        val distance =
            if (isHome) null else home?.latLng?.distanceBetween(country?.latLng)
        _selectedLocation.value = country?.copy(distanceFromHome = distance, isHome = isHome)
    }
}
