package com.alarmua.service

import android.util.Log
import com.alarmua.LOG_TAG
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService: FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(LOG_TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    /**
     * To reduce server load
     */
    private fun unregisterToken() {
        TODO("UNREGISTER OLD TOKEN ON SERVER")
        // get old token from local data store and remove it
        // unregister token on server
    }

    private fun sendRegistrationToServer(token: String) {
        TODO("REGISTER TOKEN ON SERVER")
        // save new token in local data store
        // register token on server
    }
}