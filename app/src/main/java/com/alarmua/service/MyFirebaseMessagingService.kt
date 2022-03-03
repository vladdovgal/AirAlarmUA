package com.alarmua.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.alarmua.LOG_TAG
import com.alarmua.MainActivity
import com.alarmua.R
import com.alarmua.ui.base.MyMediaPlayer
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val FCM_TOKEN_KEY = "FCM_token_key"
const val CURRENT_LOCATION_ID_KEY = "current_location_id"
const val FCM_SHARED_PREFS_FILE_NAME = "fcm_token"
const val CURRENT_LOCATION_SHARED_PREFS_FILE_NAME = "current_location"
const val NOTIFICATION_CHANNEL_ID = "alert_channel_id"
const val NOTIFICATION_CHANNEL_NAME = "Alert channel"
const val NOTIFICATION_TITLE_FIELD = "title"
const val NOTIFICATION_BODY_FIELD = "body"
const val NOTIFICATION_TYPE_FIELD = "type"
const val NOTIFICATION_TYPE_ARG = "alarm_type"
const val ALERT_TYPE_AIR_ALARM_ON = "air_alarm_on"
const val ALERT_TYPE_AIR_ALARM_OFF = "air_alarm_off"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var mp: MediaPlayer? = null

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
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationType = remoteMessage.data[NOTIFICATION_TYPE_FIELD]
        val args = Bundle()
        args.putString(NOTIFICATION_TYPE_ARG, notificationType)
        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.alertDetailsFragment)
            .setArguments(args)
            .createPendingIntent()

        val channelId = NOTIFICATION_CHANNEL_ID
        val notificationTitle = remoteMessage.data[NOTIFICATION_TITLE_FIELD]
        val notificationBody = remoteMessage.data[NOTIFICATION_BODY_FIELD]
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }


        MyMediaPlayer.startNotificationSound(this)
        manager.notify(0, builder.build())
    }

    private fun saveTokenToSharedPrefs(token: String) {
        getSharedPreferences(
            FCM_SHARED_PREFS_FILE_NAME,
            MODE_PRIVATE
        ).edit().putString(FCM_TOKEN_KEY, token).apply()
    }
}