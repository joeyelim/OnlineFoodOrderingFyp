package com.example.fyp.Util

import android.content.Context
import android.util.Log
import com.example.fyp.Class.User
import com.example.fyp.Chat.Item.PersonItem
import com.example.fyp.Chat.Item.TextMessageItem
import com.example.fyp.Chat.model.ChatChannel
import com.example.fyp.Chat.model.Message
import com.example.fyp.Chat.model.MessageType
import com.example.fyp.Chat.model.TextMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item

object FirestoreUtil {

    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUserDocRef: DocumentReference

    get() = firestoreInstance.document("User/${FirebaseAuth.getInstance().currentUser?.email
        ?:throw NullPointerException("Email is null.")}")

    private val chatChannelCollectionRef = firestoreInstance.collection("chatChannels")

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)!!)
            }
    }

    fun addUserListener(context: Context, onListItem: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("User")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE","User listener error", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val item = mutableListOf<Item>()
                querySnapshot!!.documents.forEach{
                    if (it.id != FirebaseAuth.getInstance().currentUser?.email)
                        item.add(PersonItem(it.toObject(User::class.java)!!, it.id, context))
                }
                onListItem(item)
            }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun getOrCreateChatChannel(otherUserId: String,
                               onComplete:(channelId: String) -> Unit) {
        Log.i("123","123")
        currentUserDocRef.collection("engagedChatChannels")
            .document(otherUserId).get().addOnSuccessListener {
                if(it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }
                val currentUserId = FirebaseAuth.getInstance().currentUser!!.email.toString()
                val newChannel = chatChannelCollectionRef.document()
                newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))


                currentUserDocRef
                    .collection("engagedChatChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("User").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))

                onComplete(newChannel.id)
            }
    }

    fun addChatMessageListener(channelId: String, context: Context,
                               onListen:(List<Item>)-> Unit):ListenerRegistration {
        return chatChannelCollectionRef.document(channelId).collection("message")
            .orderBy("time")
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChatMessageListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val item = mutableListOf<Item>()
                querySnapshot!!.documents.forEach{
                    if (it["type"] == MessageType.TEXT)
                        item.add(TextMessageItem(it.toObject(TextMessage::class.java)!!, context))
                    else
                        TODO("Add image message.")
                }
                onListen(item)
            }
    }

    fun sendMessage(message:Message, channelId: String) {
        chatChannelCollectionRef.document(channelId)
            .collection("message")
            .add(message)
    }
    //region FCM
    fun getFCMRegistrationTokens(onComplete: (tokens: MutableList<String>) -> Unit){
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            onComplete(user.registrationToken)
        }
    }
    fun setFCMRegistrationTokens(registratioToken: MutableList<String>){
        currentUserDocRef.update(mapOf("registrationToken" to registratioToken))
    }
    // endregion FCM
}