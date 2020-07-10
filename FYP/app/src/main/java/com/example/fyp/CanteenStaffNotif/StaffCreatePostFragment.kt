package com.example.fyp.CanteenStaffNotif


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.fyp.R
import com.example.fyp.databinding.FragmentStaffCreatePostBinding

/**
 * A simple [Fragment] subclass.
 */
class StaffCreatePostFragment : Fragment() {
    private lateinit var binding: FragmentStaffCreatePostBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.fyp.CanteenStaffNotif"
    private val description = "Test notification"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_staff_create_post, container, false
        )

            notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            binding.btnSend.setOnClickListener {

                val intent = Intent(activity, LauncherActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(activity,0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                val contentView = RemoteViews(context!!.packageName, R.layout.notification_layout)
                contentView.setTextViewText(R.id.tvTitle, "Taruc Online Foods Ordering")
                contentView.setTextViewText(R.id.tvContent, "Notification Title")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = NotificationChannel(
                        channelId,
                        description,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.GREEN
                    notificationChannel.enableVibration(false)
                    notificationManager.createNotificationChannel(notificationChannel)

                    builder = Notification.Builder(activity, channelId)
                        .setContent(contentView)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                }
                else{

                    builder = Notification.Builder(activity)
                        .setContent(contentView)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                }
                notificationManager.notify(1234, builder.build())
            }

        return binding.root
    }


}
