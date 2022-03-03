package com.alarmua.ui.locations

import com.alarmua.model.LocationDTO
import com.alarmua.network.Common
import kotlinx.coroutines.delay

class LocationsRepository {

    private val retrofitService = Common.retrofitService

    suspend fun getLocations(): List<LocationDTO> {
        // mock
        // TO DO: LOAD LOCATIONS FROM SERVER
        delay(1000)
        return listOf(
            LocationDTO("LV"),
            LocationDTO("KY"),
            LocationDTO("IF"),
            LocationDTO("TE"),
            LocationDTO("LU"),
            LocationDTO("UZ"),
            LocationDTO("PO"),
            LocationDTO("OD"),
            LocationDTO("CHK"),
            LocationDTO("CHG"),
            LocationDTO("VO"),
            LocationDTO("ZH"),
            LocationDTO("DO"),
            LocationDTO("VI"),

        )
//        return retrofitService.getLocations()
    }
}