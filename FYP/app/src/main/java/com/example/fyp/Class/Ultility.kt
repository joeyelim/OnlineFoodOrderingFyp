package com.example.fyp.Class

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

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

        fun delDialog(context: Context, cart: Cart) {
            val dialog = AlertDialog.Builder(context)
            val foodName: String? = cart.food_name

            dialog.setTitle("Confirmation")
            dialog.setMessage("Are you sure want to delete the order?\n* $foodName")
            dialog.setPositiveButton("Yes") { _: DialogInterface, i: Int -> }
            dialog.setNegativeButton("No") { _: DialogInterface, i: Int -> }
            dialog.show()
        }
    }


}