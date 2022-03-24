package com.example.europeanmap.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.europeanmap.R
import com.example.europeanmap.ui.model.Country
import com.example.europeanmap.ui.theme.EuropeanMapTheme
import com.example.europeanmap.utils.BitmapUtils
import com.example.europeanmap.utils.Constants.EUROPE
import com.example.europeanmap.utils.Constants.INITIAL_ZOOM
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EuropeanMapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val countries by viewModel.locations.collectAsState()
                    val selected by viewModel.selectedLocation.collectAsState()

                    val coroutineScope = rememberCoroutineScope()

                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(EUROPE, INITIAL_ZOOM)
                    }
                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                    )

                    SideEffect {
                        coroutineScope.launch {
                            if (selected != null) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }

                    BottomSheetScaffold(
                        scaffoldState = bottomSheetScaffoldState,
                        sheetContent = {
                            selected?.let {
                                CountrySheet(it) { home ->
                                    viewModel.setHome(home)
                                }
                            }
                        },
                        sheetPeekHeight = 0.dp
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            onMapClick = {
                                viewModel.selectLocation(null)
                            }
                        ) {
                            countries?.map { mCountry ->
                                val icon = if (mCountry.isHome) BitmapUtils.bitmapDescriptor(
                                    LocalContext.current,
                                    R.drawable.ic_home_map_location
                                ) else null
                                Marker(
                                    state = MarkerState(
                                        position = mCountry.latLng
                                    ),
                                    icon = icon,
                                    onClick = {
                                        viewModel.selectLocation(country = mCountry)
                                        return@Marker false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountrySheet(country: Country, setAsHomeClicked: (Country) -> Unit) {
    Box(
        Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topEnd = 16.dp, topStart = 16.dp
                )
            )
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = country.name,
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = country.capital,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TimeZone(s) : ${country.timezones.joinToString(", ")}",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )

            if (country.distanceFromHome != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Distance from home : ${country.distanceFromHome} KM",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (country.isHome.not()) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        setAsHomeClicked(country)
                    }
                ) {
                    Text(text = "Set as Home")
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Home Location")
            }
        }
    }
}
