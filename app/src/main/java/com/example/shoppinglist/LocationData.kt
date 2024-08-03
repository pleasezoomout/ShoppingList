package com.example.shoppinglist

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class GeocodingReponse(
    val results: List<GeocodingResult>,
    val status: String
)

class GeocodingResult(
    val formatedAddress: String,
)
