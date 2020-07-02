package com.example.fyp.Class

import android.widget.TextView

interface OnAdapterItemClick{
    fun addBtnClick(cart : Cart, view : TextView, txt : TextView)
    fun minusBtnClick(cart : Cart, view : TextView, txt : TextView)
    fun deleteBtnClick(cart: Cart)
}