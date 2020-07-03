package com.example.fyp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _activeButton = MutableLiveData<Int>()
    val activeButton: LiveData<Int>
        get() = _activeButton

    fun activateCartButton() {
        _activeButton.value = _activeButton.value?.plus(1)
    }

    fun deActivateCartButton() {
        _activeButton.value = _activeButton.value?.minus(1)
    }

    init {
        _activeButton.value = 0
    }

}