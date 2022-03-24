package com.example.europeanmap.usecases

import android.content.Context
import com.example.europeanmap.usecases.model.Country
import com.example.europeanmap.utils.FileReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.lang.reflect.Type
import javax.inject.Inject

@ViewModelScoped
class GetLocationsFromDeviceUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
     fun execute(): List<Country>? {
        val listOfMyClassObject: Type = object : TypeToken<ArrayList<Country?>?>() {}.type
        return gson.fromJson<List<Country>>(
            FileReader.readStringFromFile("countries.json"),
            listOfMyClassObject
        )
    }
}