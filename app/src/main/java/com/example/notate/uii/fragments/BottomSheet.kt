package com.example.notate.uii.fragments

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.notate.BroadCast.AlarmReceiver
import com.example.notate.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_detailes.*
import java.util.*

class BottomSheet : BottomSheetDialogFragment() {


    val channelId = "channelId"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)

        createNotificationChannel()

       view.setReminder.setOnClickListener {
           scheduleNotification()
       }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {

        val intent = Intent(requireContext() , AlarmReceiver::class.java)

        if (this.requireParentFragment().noteTitle.text.isNotEmpty()){
            intent.putExtra("noteTitle" , "Don't forget your \"${this.requireParentFragment().noteTitle.text}\" note ")

        } else {
            intent.putExtra("noteTitle" , "Don't forget your note")

        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {
        val min = timePicker.minute
        val hour = timePicker.hour
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year , month , day , hour , min)

        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Descreption"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId ,name , importance )
        channel.description = desc

        val notificationManager = requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}