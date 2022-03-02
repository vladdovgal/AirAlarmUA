package com.alarmua.network

object Common {
    private const val BASE_URL = "https://www.tbd.net/demos/"

    val retrofitService: Api
        get() = RetrofitClient.getClient(BASE_URL).create(Api::class.java)
}