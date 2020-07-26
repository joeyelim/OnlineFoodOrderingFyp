package com.example.fyp.Util

import android.content.Context
import android.util.Log
import com.example.fyp.Class.User
import com.example.fyp.Chat.Item.PersonItem
import com.example.fyp.Chat.Item.TextMessageItem
import com.example.fyp.Chat.model.ChatChannel
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

    get() = firestoreInstance.document("User/${FirebaseAuth.getInstance().currentUser?.uid
        ?:throw NullPointerException("Email is null.")}")

    private val chatChannelCollectionRef = firestoreInstance.collection("chatChannels")


    fun addUserListener(context: Context, onListItem: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("User")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE","User listener error", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val item = mutableListOf<Item>()
                querySnapshot!!.documents.forEach{
                    if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                        item.add(PersonItem(it.toObject(User::class.java)!!, it.id, context))
                }
                onListItem(item)
            }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun getOrCreateChatChannel(otherUserId: String,
                               onComplete:(channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels")
            .document(otherUserId).get().addOnSuccessListener {
                if(it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }
                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                val newChannel = chatChannelCollectionRef.document()
                newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                currentUserDocRef
                    .collection("engagedChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("users").document(otherUserId)
                    .collection("engagedChannels")
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
}