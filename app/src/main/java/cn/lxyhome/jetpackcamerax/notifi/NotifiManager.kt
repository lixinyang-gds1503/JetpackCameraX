package cn.lxyhome.jetpackcamerax.notifi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.UserInfoActivity

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/4/26 created.
 *""
 *
 */
object NotifiManager {
    private const val DEFAULT_CHANNEL_ID = "jetapp_channel_id"

    fun createNotifiction(context: Context, message: String, title: String):NotificationCompat.Builder {
       return NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID).setContentTitle(title)
                .setContentText(message)
                .setContentIntent(createPendingIntent(context))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
    }

    private fun createPendingIntent(context: Context):PendingIntent {
         return PendingIntent.getActivity(context,0, Intent(context,UserInfoActivity::class.java).also{
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }, 0)

    }

    fun createNotifictionStyle(context: Context, message: String, title: String,longmessage:String):NotificationCompat.Builder {
       return NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID).setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(longmessage))
                .setContentIntent(createPendingIntent(context))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
    }

     fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DEFAULT_CHANNEL_ID, "jetapp8.0+", importance).apply {
                description = "通知"
            }
            // Register the channel with the system
           JetpackApplication.self?.let {
               val notificationManager: NotificationManager =
                   getSystemService(it,NotificationManager::class.java) as NotificationManager
               notificationManager.createNotificationChannel(channel)
            }

        }
    }

    fun notifi(context: Context) {
        NotificationManagerCompat.from(context).run {
            this.notify(1, createNotifiction(context,"The is test notifiction","Title").build())
        }
    }
}