package com.alarmua.ui.locations

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.alarmua.LOG_TAG
import com.alarmua.ui.base.BaseViewModel
import com.alarmua.ui.locations.list.toItem
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LocationsListViewModel : BaseViewModel() {

//    private val _locations: MutableLiveData<List<LocationItem>> = MutableLiveData(emptyList())
//    val locations: LiveData<List<LocationItem>> = _locations

    val locationItems = liveData(Dispatchers.IO) {
        withLoading {
            emit(repository.getLocations().map { dto ->
                dto.toItem()
            })
        }
    } as MutableLiveData

    private val repository = LocationsRepository()

    val isSaveButtonEnabled = locationItems.map { locations ->
        locations.any { it.isSelected }
    }

    private val _navigationDestination: MutableLiveData<NavDirections?> = MutableLiveData(null)
    val navigationDestination: LiveData<NavDirections?> = _navigationDestination

    fun selectItem(itemId: String) {
        viewModelScope.launch {
            val locations = locationItems.value ?: emptyList()
            locations.forEach { item ->
                item.isSelected = item.id == itemId
            }
            locationItems.value = locations
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
                    navigateToSuccessScreen(selectedLocationId)
                } else {
                    showToast("You are already subscribed for $selectedLocationId updates")
                }
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
                Log.w(LOG_TAG, msg)
            }.await()
    }

    private fun navigateToSuccessScreen(locationId: String) {
        _navigationDestination.value = LocationsListFragmentDirections.actionLocationsListFragmentToSuccessActionFragment(locationId)
    }

}