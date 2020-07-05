package com.example.fyp.Class

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Ultility {

    // this is equal to Java static
    companion object {
        fun openDialog(context: Context) {
            val dialog = AlertDialog.Builder(context)

            dialog.setTitle("Oops, sorry!")
            dialog.setMessage("Your order quantity has exceeded the maximum inventory, please select again.")
            dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
            dialog.show()
        }


    }



}