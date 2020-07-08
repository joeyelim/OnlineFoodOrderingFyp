package com.example.fyp.Interface

import android.widget.CheckBox
import android.widget.TextView
import com.example.fyp.Class.Cart

interface OnAdapterItemClick{
    fun addBtnClick(cart : Cart, view : TextView, txt : TextView, cb : CheckBox)
    fun minusBtnClick(cart : Cart, view : TextView, txt : TextView, cb :CheckBox)
    fun deleteBtnClick(cart: Cart)
    fun checkBoxClick(cart : Cart, checkBox: CheckBox, view : TextView)
}