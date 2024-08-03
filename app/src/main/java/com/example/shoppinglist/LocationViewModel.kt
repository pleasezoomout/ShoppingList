package com.example.shoppinglist

import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class LocationViewModel: ViewModel()  {

    private val _locationData = MutableLiveData<LocationData>()
    val locationData: LiveData<LocationData> = _locationData

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(locationData: LocationData){
        _locationData.value = locationData

    }
    fun fetchAddress(latlng: String){
        try{
            viewModelScope.launch {

                val result = RetrofitClient.createRequest().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyAflrVRz3OyhwTgrw4TNCRHJauB4gWyN0g"
                )
                _address.value = result.results
            }
        }catch(e:Exception) {
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
}