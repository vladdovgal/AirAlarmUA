package com.alarmua.network

import com.alarmua.model.LocationDTO
import retrofit2.http.GET


interface Api {

    @GET("locations")
    suspend fun getLocations(): List<LocationDTO>
}