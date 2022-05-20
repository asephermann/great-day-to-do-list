package com.asephermann.greatdaytodolist

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class Notification : BroadcastReceiver() {


    companion object {
        const val notificationId = 1
        const val channelId = "default"
        const val titleExtra = "titleExtra"
        const val messageExtra = "messageExtra"

    }
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.todo)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(intent.getStringExtra(messageExtra))
            )
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }
}