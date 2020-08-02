package com.example.fyp.Chat.Item

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.Chat.model.TextMessage
import com.example.fyp.Interface.OnAdapterItemClick
import com.example.fyp.R
import com.example.fyp.Util.FirestoreUtil
import com.example.fyp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.item_text_msg.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat


class TextMessageItem(val message: TextMessage,
                      val context: Context
                        )
    :Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.txt_message.text = message.text
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }

    private fun setTimeText(viewHolder: ViewHolder) {
        val dateFormat = SimpleDateFormat
            .getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
        viewHolder.txt_message_time.text = dateFormat.format(message.time)
    }

    private fun setMessageRootGravity(viewHolder: ViewHolder) {

        if (message.senderId == FirebaseAuth.getInstance().currentUser?.email) {
            viewHolder.message_root.apply {
                setBackgroundResource(com.example.fyp.R.drawable.my_bubble_shape)
                val lParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END)
                this.layoutParams =lParams
            }
        } else {
                viewHolder.message_root.apply {
                    setBackgroundResource(com.example.fyp.R.drawable.friend_buble_shape)
                    val lParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START)
                    this.layoutParams =lParams
                }
            }

        viewHolder.message_root.setOnClickListener {
            val textMessage: TextMessage = TextMessage()

            val dialog = AlertDialog.Builder(context)



//            val documentId = FirebaseFirestore.getInstance()
//                .collection("chatChannels").document().getId()
//            val documentId1 = FirebaseFirestore.getInstance()
//                .collection("chatChannels").document(documentId)
//                .collection("message").document().getId()

//            val i = FirebaseAuth.getInstance().currentUser?.email
            Log.i("testing", FirebaseAuth.getInstance().currentUser?.email)

            val channelId = FirebaseFirestore.getInstance()
                .collection("User")
                .document(FirebaseAuth.getInstance().currentUser?.email!!)
                .collection("engagedChatChannels")
                .document(it.email.toString())
            Log.i("testing", it.email.toString())
            Log.i("testing", channelId.toString())


            dialog.setTitle("Delete")
            dialog.setMessage("Are you sure want to delete this message?")

            dialog.setPositiveButton("Delete") { _: DialogInterface, _: Int ->

                FirebaseFirestore.getInstance()
                    .collection("chatChannels").document()
                    .collection("message").document()
                    .delete()
                    .addOnCompleteListener {
                        Toast.makeText(context, "Deleted.", Toast.LENGTH_LONG).show()
                    }
            }
            dialog.setNegativeButton("No") { _: DialogInterface, _: Int -> }
            dialog.show()
         }

    }

    override fun getLayout() = R.layout.item_text_msg



    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is TextMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? TextMessageItem)
    }

//    override fun hashCode(): Int {
//        var result = message.hashCode()
//        result = 31 * result + context.hashCode()
//        return result
//    }

}