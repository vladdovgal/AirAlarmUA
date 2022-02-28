package com.alarmua.service

import android.content.Context
import android.util.Log
import com.alarmua.LOG_TAG
import com.google.firebase.messaging.FirebaseMessagingService

const val FCM_TOKEN_KEY = "FCM_token_key"
const val CURRENT_LOCATION_ID_KEY = "current_location_id"
const val FCM_SHARED_PREFS_FILE_NAME = "fcm_token"
const val CURRENT_LOCATION_SHARED_PREFS_FILE_NAME = "current_location"

class MyFirebaseMessagingService: FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(LOG_TAG, "Refreshed token: $token")

        saveTokenToSharedPrefs(token)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    private fun saveTokenToSharedPrefs(token: String) {
        getSharedPreferences(
            FCM_SHARED_PREFS_FILE_NAME,
            MODE_PRIVATE
        ).edit().putString(FCM_TOKEN_KEY, token).apply();
    }


    /**
     * To reduce server load
     */
    private fun unregisterToken() {
        // get old token from local data store and remove it
        // unregister token on server
    }

    private fun sendRegistrationToServer(token: String) {
        // save new token in local data store
        // register token on server
    }

    companion object {
        fun getToken(context: Context): String? {
            return context.getSharedPreferences(
                FCM_SHARED_PREFS_FILE_NAME,
                MODE_PRIVATE
            ).getString(FCM_TOKEN_KEY, "empty")
        }
    }
}