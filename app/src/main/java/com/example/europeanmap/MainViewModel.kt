package com.example.europeanmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.europeanmap.usecases.GetLocationsFromDeviceUseCase
import com.example.europeanmap.usecases.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocationsFromDeviceUseCase: GetLocationsFromDeviceUseCase
) : ViewModel() {
    private val _locations = MutableStateFlow<List<Country>?>(null)
    val locations = _locations.asStateFlow()

    init {
        getLocationsFromAssets()
    }

    private fun getLocationsFromAssets(){
        viewModelScope.launch {
            val data = getLocationsFromDeviceUseCase.execute()
            _locations.value = data
        }
    }
}