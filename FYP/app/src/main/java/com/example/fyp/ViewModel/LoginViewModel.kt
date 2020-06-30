package com.example.fyp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.User

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

    //    private fun getCurrentUser(user : FirebaseUser) {
//        var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//        //var user2 : CurrentUser
//        val email : String = user.email?:""
//        val docRef = mFirestore.collection("User")
//            .document(email)
//
//        Log.i("user", docRef.get().isSuccessful.toString())
//
//        docRef
//            .get().addOnSuccessListener { documentSnapshot ->
//                Log.i("user22", user.email)
//                _currentUser.value = documentSnapshot.toObject(CurrentUser::class.java)
//            }
//    }
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
