package com.fellowcar.android.data.network.model.osrm

/**
* Fellowcar project
* Created by ANDREY. Y on 1/18/2018.
* Email: inittey@gmail.com
*/

data class GeocodingResponseItem(
        val lat: Double,
        val lon: Double,
        val osm_type: String,
        val display_name: String,
        val type: String)