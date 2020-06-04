package com.example.fyp.LoginModule

/*
*   To enable binding
* 1. Cover your layout with <layout> tag 参考 login fragment 的 layout
* 2. private lateinit var 还有 initialize binding (参考下面的 code)
* 3. return binding.root
* 4. 过后你要 target layout 的 control 就可以用 binding.textbox 这样的
*
*
* */

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        // use for binding, remember to set the layout cover by <layout> tag
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        // option menu
        setHasOptionsMenu(true)

        binding.btnSignUp.setOnClickListener {

            it.findNavController()
                .navigate(LoginFragmentDirections.actionLoginToRegistration())
        }

        binding.btnForgotPassword.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections.actionFragmentLoginToForgotPasswordFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // 拿掉 option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    // 这个是 display bottom navbar, 当你退出这个 fragment 的时候
    override fun onDestroyView() {
        super.onDestroyView()
//        (activity as MainActivity).setNavVisible()
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

}
