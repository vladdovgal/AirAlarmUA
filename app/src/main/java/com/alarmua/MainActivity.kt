package com.alarmua

import android.content.Context
import android.os.BaseBundle
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.alarmua.service.CURRENT_LOCATION_ID_KEY
import com.alarmua.service.CURRENT_LOCATION_SHARED_PREFS_FILE_NAME
import com.alarmua.service.NOTIFICATION_TYPE_ARG
import com.alarmua.ui.locations.LocationsListFragmentDirections

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val sharedPreferences = getSharedPreferences(CURRENT_LOCATION_SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val lastLocationId = sharedPreferences.getString(CURRENT_LOCATION_ID_KEY, null)

        val receivedBundle = intent?.extras?.get(NavController.KEY_DEEP_LINK_EXTRAS) as? BaseBundle
        val isReceivedArgsEmpty = receivedBundle?.isEmpty ?: true
        // navigate to dashboard if user subscribed for any updates
        if (lastLocationId != null && isReceivedArgsEmpty) {
            navController.navigate(
                LocationsListFragmentDirections.actionLocationsListFragmentToMainFragment()
            )
        }
    }
}