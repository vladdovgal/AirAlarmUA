package com.alarmua.ui.main

import com.alarmua.R
import com.alarmua.ui.base.BaseViewModel
import com.alarmua.ui.locations.list.DashboardItem

class MainViewModel : BaseViewModel() {

    fun getDashboardLinks(): List<DashboardItem> {
        return List(6) {
            DashboardItem(
                R.string.support_army,
                R.drawable.ic_edit_location,
                "dummy url"
            )
        }
    }
}