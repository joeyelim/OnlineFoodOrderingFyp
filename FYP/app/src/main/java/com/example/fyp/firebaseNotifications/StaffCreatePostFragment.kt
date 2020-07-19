package com.example.fyp.firebaseNotifications


import android.app.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.Class.Notification
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentStaffCreatePostBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_staff_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
const val TOPIC = "/topics/myTopic"

class StaffCreatePostFragment : Fragment() {
    private lateinit var binding: FragmentStaffCreatePostBinding
    lateinit var notificationManager: NotificationManager
    private lateinit var userViewModel: UserViewModel
//    lateinit var notificationChannel: NotificationChannel
//    lateinit var builder: Notification.Builder
//    private val channelId = "com.example.fyp.CanteenStaffNotif"
//    private val description = "Test notification"


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

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // send to a specific single device
        // get token
        MyFirebaseMessagingService.sharedPref = this.activity!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            MyFirebaseMessagingService.token = it.token
            txtToken.setText(it.token)
        }

        // pass the topic here
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.btnSend.setOnClickListener {
            val title = binding.editTxtTitle.text.toString()
            val content = binding.editTxtContent.text.toString()
            val recipientToken = binding.txtToken.text.toString()
            val recipient = binding.editTxtReceiver.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, content, recipient),
                    // to all user that have the same topic
                    //TOPIC
                    // to a single user with that token
                    recipientToken

                ).also {
                    sendNotification(it)
                    intiUI()
                }
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
//


        return binding.root
    }

    private fun intiUI() {
        binding.txtStaffStoreName.text = userViewModel.user?.store
        val db = FirebaseFirestore.getInstance()
        val notificationID = Random.nextInt().toString()
        val calForDate = Calendar.getInstance().time
        val currentDate = SimpleDateFormat("dd.MM.yyyy").format(calForDate)
        val currentTime = SimpleDateFormat("HH:mm").format(calForDate)
        val notiList = ArrayList<Notification>()
        notiList.add(
            Notification(
                notificationID, userViewModel.user?.store,binding.editTxtTitle.text.toString(),
                binding.editTxtContent.text.toString(),currentDate,currentTime,false
            )
        )

        // upload item into cart
        db.runBatch {
            for ((index, item) in notiList.withIndex()) {
                it.set(db.collection("User").document(binding.editTxtReceiver.text.toString())
                    .collection("Notification").document(item.notif_ID!!), item)
            }

        }.addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    activity, "New notification have been created",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
