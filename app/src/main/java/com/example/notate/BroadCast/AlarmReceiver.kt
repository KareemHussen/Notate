package com.example.notate.BroadCast

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notate.R
import com.example.notate.uii.fragments.MainActivity
import kotlinx.android.synthetic.main.fragment_detailes.*

const val id = 1
const val channelId = "channelId"

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var args = intent!!.getStringExtra("noteTitle")

        var bundle = Bundle()
        bundle.putString("note" , args.toString())


        val intent = Intent(context , MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1 , PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(context!! , channelId)
            .setSmallIcon(R.drawable.reminder_icon)
            .setContentTitle("My Notes")
            .setContentText(args!!.toString())
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManger = NotificationManagerCompat.from(context)
        notificationManger.notify(id , builder.build())
    }
}