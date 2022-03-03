package com.alarmua.ui.locations.list

import androidx.annotation.StringRes
import com.alarmua.R
import com.alarmua.model.LocationDTO
import com.alarmua.model.LocationItem

fun LocationDTO.toItem(): LocationItem {
    return LocationItem(
        id = locationId,
        readableName = mapIdToReadableName(locationId),
    )
}

fun mapIdToReadableName(locationId: String): Int {
    return when (locationId) {
        "LV" -> R.string.lviv
        "IF" -> R.string.ivano_frankivsk
        "VO" -> R.string.volyn
        "TE" -> R.string.ternopil
        "UZ" -> R.string.uzhorod
        "CHZ" -> R.string.chernivtsi
        "KY" -> R.string.kyiv
        "OD" -> R.string.odesa
        "HE" -> R.string.herson
        "CHG" -> R.string.chernihiv
        "ZH" -> R.string.zhytomyr
        "HM" -> R.string.khmelnitsky
        "SY" -> R.string.symy
        "RI" -> R.string.rivne
        "PO" -> R.string.poltava
        "ZA" -> R.string.zaporizha
        "DO" -> R.string.donetsk
        "LU" -> R.string.luhansk
        "VI" -> R.string.vinnisty
        "DN" -> R.string.dnipro
        "KR" -> R.string.kropyvnysky
        "MY" -> R.string.mykolaiv
        "KH" -> R.string.kharkiv
        "CHK" -> R.string.cherkasy
        else -> R.string.not_supported
    }
}