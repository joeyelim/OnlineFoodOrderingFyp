package com.example.fyp.Interface

import android.widget.CheckBox
import android.widget.TextView
import com.example.fyp.Class.Cart

interface OnAdapterItemClick{
    fun addBtnClick(cart : Cart, view : TextView, txt : TextView)
    fun minusBtnClick(cart : Cart, view : TextView, txt : TextView)
    fun deleteBtnClick(cart: Cart)
    fun checkBoxClick(cart : Cart, checkBox: CheckBox)
}