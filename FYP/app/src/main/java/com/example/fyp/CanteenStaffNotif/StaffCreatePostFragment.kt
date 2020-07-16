package com.example.fyp.CanteenStaffNotif


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentStaffCreatePostBinding
import com.example.fyp.firebaseNotifications.MyFirebaseMessagingService
import com.example.fyp.firebaseNotifications.NotificationData
import com.example.fyp.firebaseNotifications.PushNotification
import com.example.fyp.firebaseNotifications.RetrofitInstance
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_staff_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
const val TOPIC = "/topics/myTopic"

class StaffCreatePostFragment : Fragment() {
    private lateinit var binding: FragmentStaffCreatePostBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.fyp.CanteenStaffNotif"
    private val description = "Test notification"

    val TAG = "StaffCreatePostFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_staff_create_post, container, false
        )
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        MyFirebaseMessagingService.sharedPref = this.activity!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            MyFirebaseMessagingService.token = it.token
            txtToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.btnSend.setOnClickListener {
            val title = binding.editTxtTitle.text.toString()
            val content = binding.editTxtContent.text.toString()
            val recipientToken = binding.txtToken.text.toString()

            if(title.isNotEmpty() && content.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title,content),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }

//            val intent = Intent(activity, LauncherActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(activity,0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
//
//            val contentView = RemoteViews(context!!.packageName, R.layout.notification_layout)
//            contentView.setTextViewText(R.id.tvTitle, "Taruc Online Foods Ordering")
//            contentView.setTextViewText(R.id.tvContent, "Notification Title")
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationChannel = NotificationChannel(
//                    channelId,
//                    description,
//                    NotificationManager.IMPORTANCE_HIGH
//                )
//                notificationChannel.enableLights(true)
//                notificationChannel.lightColor = Color.GREEN
//                notificationChannel.enableVibration(false)
//                notificationManager.createNotificationChannel(notificationChannel)
//
//                builder = Notification.Builder(activity, channelId)
//                    .setContent(contentView)
//                    .setSmallIcon(R.mipmap.ic_launcher_round)
//                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
//                    .setContentIntent(pendingIntent)
//            }
//            else{
//
//                builder = Notification.Builder(activity)
//                    .setContent(contentView)
//                    .setSmallIcon(R.mipmap.ic_launcher_round)
//                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
//                    .setContentIntent(pendingIntent)
//            }
//                notificationManager.notify(1234, builder.build())

        }

        return binding.root
    }

    private fun sendNotification(notification:PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try{
            val respone = RetrofitInstance.api.postNotification(notification)
            if(respone.isSuccessful) {
                Log.d(TAG, "Respone: ${Gson().toJson(respone)}")
            }else {
                Log.e(TAG, respone.errorBody().toString())
            }
        }catch (e:Exception) {
            Log.e(TAG, e.toString())
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}
