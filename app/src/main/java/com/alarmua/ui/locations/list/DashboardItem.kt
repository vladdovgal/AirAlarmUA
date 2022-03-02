package com.alarmua.ui.locations.list

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class DashboardItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
    val url: String,
)
