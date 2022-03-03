package com.alarmua.ui.base

import android.content.Context
import android.media.MediaPlayer
import com.alarmua.R

object MyMediaPlayer {
    var mediaPlayer: MediaPlayer? = null

    fun startNotificationSound(context: Context) {
        stopNotificationSound()

        mediaPlayer = MediaPlayer.create(context, R.raw.airsiren)
        (mediaPlayer as MediaPlayer).start()
    }

    fun stopNotificationSound() {
        mediaPlayer?.stop()
        mediaPlayer?.seekTo(0)
    }
}