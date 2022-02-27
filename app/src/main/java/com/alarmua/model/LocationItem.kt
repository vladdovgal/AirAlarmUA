package com.alarmua.model

data class LocationItem(
  val id: String = "NO_ID",
  val name: String,
  var isSelected: Boolean = false,
)