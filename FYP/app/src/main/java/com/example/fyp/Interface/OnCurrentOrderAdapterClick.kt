package com.example.fyp.Interface

import android.widget.Button
import com.example.fyp.Class.Order_Food

interface OnCurrentOrderAdapterClick {
    fun buttonClick(order : Order_Food, view : Button)
}