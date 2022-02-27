package com.alarmua.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.alarmua.model.LocationItem
import com.alarmua.ui.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationsListViewModel : BaseViewModel() {

    private val _locations: MutableLiveData<List<LocationItem>> = MutableLiveData(emptyList())
    val locations: LiveData<List<LocationItem>> = _locations

    val isSaveButtonEnabled = _locations.map { locations ->
        locations.any { it.isSelected }
    }

    init {
        viewModelScope.launch {
            loading(true)
            // MOCK fetching locations
            // repository.fetchLocations()
            delay(1000)
            _locations.value = listOf(
                LocationItem("LV", "Lviv disctrict"),
                LocationItem("KY", "Kyiv disctrict"),
                LocationItem("IF", "Ivano-Frankivsk disctrict"),
                LocationItem("TE", "Ternopil disctrict"),
                LocationItem("T1", "Ternopil disctrict"),
                LocationItem("T2", "Ternopil disctrict"),
                LocationItem("T3", "Ternopil disctrict"),
                LocationItem("T4", "Ternopil disctrict"),
                LocationItem("T5", "Ternopil disctrict"),
                LocationItem("T6", "Ternopil disctrict"),
                LocationItem("T7", "Ternopil disctrict"),
                LocationItem("T9", "Ternopil disctrict"),
                LocationItem("T10", "Ternopil disctrict"),
                LocationItem("T11", "Ternopil disctrict"),
                LocationItem("T12", "Ternopil disctrict"),
                LocationItem("T13", "Ternopil disctrict"),
            )
            loading(false)
        }
    }

    fun selectItem(itemId: String) {
        viewModelScope.launch {
            val locations = _locations.value ?: emptyList()
            locations.forEach { item ->
                item.isSelected = item.id == itemId
            }
            _locations.value = locations
        }
    }

    fun saveLocation(locationId: String) {
        viewModelScope.launch {
            loading(true)
            delay(1000)
            // send location to server
            FirebaseMessaging.getInstance().subscribeToTopic(locationId)
                .addOnCompleteListener { task ->
                    var msg = "Subscribed to $locationId successfully"
                    if (!task.isSuccessful) {
                        msg = "Failure occurred while trying to subscribe to $locationId"
                    }
                    showToast(msg)
                }
            loading(false)
            // navigate to main screen
        }
    }

}