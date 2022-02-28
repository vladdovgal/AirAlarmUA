package com.alarmua.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.alarmua.model.LocationItem
import com.alarmua.ui.base.BaseViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LocationsListViewModel : BaseViewModel() {

    private val _locations: MutableLiveData<List<LocationItem>> = MutableLiveData(emptyList())
    val locations: LiveData<List<LocationItem>> = _locations

    val isSaveButtonEnabled = _locations.map { locations ->
        locations.any { it.isSelected }
    }

    private val _navigationDestination: MutableLiveData<NavDirections?> = MutableLiveData(null)
    val navigationDestination: LiveData<NavDirections?> = _navigationDestination

    init {
        viewModelScope.launch {
            withLoading {
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
            }
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

    fun saveLocation(lastLocationId: String?, selectedLocationId: String) {
        viewModelScope.launch {
            withLoading {
                if (lastLocationId != null && selectedLocationId != lastLocationId) {
                    unsubscribeFromLocationUpdates(lastLocationId)
                }
                if (lastLocationId != selectedLocationId) {
                    subscribeOnLocationUpdates(selectedLocationId)
                } else {
                    showToast("You are already subscribed for $selectedLocationId updates")
                }
                navigateToMainScreen()
            }
        }
    }

    private suspend fun unsubscribeFromLocationUpdates(locationId: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(locationId)
            .addOnCompleteListener { task ->
                var msg = "Unsubscribed from $locationId successfully"
                if (!task.isSuccessful) {
                    msg = "Failure occurred while trying unsubscribe from $locationId"
                }
                showToast(msg)
            }.await()
    }

    private suspend fun subscribeOnLocationUpdates(locationId: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(locationId)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $locationId successfully"
                if (!task.isSuccessful) {
                    msg = "Failure occurred while trying to subscribe to $locationId"
                }
                showToast(msg)
            }.await()
    }

    private fun navigateToMainScreen() {
        _navigationDestination.value = LocationsListFragmentDirections.actionLocationsListFragmentToMainFragment()
    }

//    suspend fun <T> Task<T>.awaitTask(): T? =
//        suspendCoroutine { continuation ->
//            addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    continuation.resume(task.result) //what to do if task.result is null
//                } else {
//                    continuation.resumeWithException(task.exception)
//                }
//            }
//        }

}