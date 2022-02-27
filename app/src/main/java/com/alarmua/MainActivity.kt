package com.alarmua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alarmua.ui.locations.LocationsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LocationsListFragment())
                .commitNow()
        }
    }
}