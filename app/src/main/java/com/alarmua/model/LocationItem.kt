package com.alarmua.model

import androidx.annotation.StringRes

data class LocationItem(
  val id: String = "NO_ID",
  @StringRes val readableName: Int,
  var isSelected: Boolean = false,
)