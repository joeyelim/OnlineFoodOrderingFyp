package com.example.fyp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser


    private val _changeMenuOption = MutableLiveData<Boolean>()
    val changeMenuOption: LiveData<Boolean>
        get() = _changeMenuOption

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState: LiveData<AuthenticationState>? =
        Transformations.map(FirebaseUserLiveData()) {
            if (it != null && it.isEmailVerified) {
                // user = UserLog(it)
                //getCurrentUser(it)
                AuthenticationState.AUTHENTICATED
            } else {
                AuthenticationState.UNAUTHENTICATED
        }

        }

    fun changeOption(option: Boolean) {
        _changeMenuOption.value = option
    }

    fun setCurrentUser() {
        _currentUser.value = User("asd", "asd", "ads", "asd", "ad", "asd", "email")
    }


    init {
        _changeMenuOption.value = false
        _currentUser.value = User()
    }


}
