package com.example.fyp.Interface

import com.example.fyp.Class.Order_Food

interface OnHistoryItemClick {
    fun buttonClickNavigate(order: Order_Food, position: Int) {}
}