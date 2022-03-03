package com.alarmua.ui.main

import androidx.lifecycle.viewModelScope
import com.alarmua.R
import com.alarmua.ui.base.BaseViewModel
import com.alarmua.ui.locations.list.DashboardItem
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : BaseViewModel() {

    fun getDashboardLinks(): List<DashboardItem> {
        val saveLifeInUa = DashboardItem(
            R.string.support_army,
            R.drawable.ic_edit_location,
            "https://savelife.in.ua/donate/"
        )

        val ukraineAvengerBot = DashboardItem(
            R.string.share_enemy_actions,
            R.drawable.ic_no_marks,
            "https://t.me/ukraine_avanger_bot"
        )

        val findPlaceToStay = DashboardItem(
            R.string.find_place_to_stay,
            R.drawable.ic_night_shelter,
        "https://prykhystok.in.ua/"
        )

        val findShelter = DashboardItem(
            R.string.shelters_map,
            R.drawable.ic_security,
            "https://ukraine.segodnya.ua/amp-ukraine/gde-nahodyatsya-bomboubezhishcha-v-raznyh-gorodah-ukrainy-spisok-1596663.html"
        )

        return listOf(
            findShelter,
            findPlaceToStay,
            saveLifeInUa,
            ukraineAvengerBot,
            DashboardItem(
                R.string.support_army,
                R.drawable.ic_edit_location,
                "dummy url"
            )
        )
    }

    fun unsubscribeFromLocationUpdates(locationId: String) {
        viewModelScope.launch {
            withLoading {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(locationId)
                    .addOnCompleteListener { task ->
                        var msg = "Unsubscribed from $locationId successfully"
                        if (!task.isSuccessful) {
                            msg = "Failure occurred while trying unsubscribe from $locationId"
                        }
                        showToast(msg)
                    }.await()
            }
        }
    }
}