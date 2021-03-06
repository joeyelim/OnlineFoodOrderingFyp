package com.example.fyp.stuMaintenanceModule


import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_profile, container, false
        )

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        binding.btnConfirm.setOnClickListener {
            if (!validation()) {
                return@setOnClickListener
            } else {
                updateDatabase()
            }
        }

        binding.btnCancel.setOnClickListener(){
            it.findNavController()
                .navigate(EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment())
        }

        return binding.root
    }

    private fun updateDatabase() {
        userViewModel.user?.first_name = binding.txtFirstN.text.toString()
        userViewModel.user?.last_name = binding.txtLastN.text.toString()
        userViewModel.user?.phone_number = binding.txtPhone.text.toString()

        FirebaseFirestore.getInstance()
            .collection("User").document(userViewModel.user?.email!!)
            .update(
                "first_name",
                binding.txtFirstN.text.toString(),
                "last_name",
                binding.txtLastN.text.toString(),
                "phone_number",
                binding.txtPhone.text.toString()
            )
            .addOnSuccessListener {
                Log.i("Test", "Success Update")
            }
            .addOnCompleteListener {
                val snackbar = Snackbar.make(
                    this.requireView(),
                    "Profile Being Updated",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setAction("Close") {
                    snackbar.dismiss()
                }
                snackbar.show()
            }
    }

    private fun intiUI() {
        binding.txtFirstN.setText(userViewModel.user?.first_name)
        binding.txtLastN.setText(userViewModel.user?.last_name)
        binding.txtEmail.setText(userViewModel.user?.email)
        binding.txtPhone.setText(userViewModel.user?.phone_number)
    }

    private fun validation(): Boolean {

        val firstName = binding.txtFirstN.text.toString()
        val lastName = binding.txtLastN.text.toString()
        val phone = binding.txtPhone.text.toString()



        if (firstName.isEmpty() || !isValidFName(firstName)
            || lastName.isEmpty() || !isValidLName(lastName)
            || phone.isEmpty() || !isValidPhoneNo(phone)) {

            if (firstName.isEmpty()) {
                binding.txtFirstNLayout.error = "*First name is require."
            }
            else if (!isValidFName(firstName)) {
                binding.txtFirstNLayout.error = "*Invalid first name."
            }
            else {
                binding.txtFirstNLayout.isErrorEnabled = false
            }

            if (lastName.isEmpty()) {
                binding.txtLastNLayout.error = "*Last name is require."
            }
            else if (!isValidFName(lastName)) {
                binding.txtLastNLayout.error = "*Invalid last name."
            }
            else {
                binding.txtLastNLayout.isErrorEnabled = false
            }

            if (phone.isEmpty()) {
                binding.txtPhoneLayout.error = "*Phone number is require."
            }
            else if (!isValidPhoneNo(phone)) {
                binding.txtPhoneLayout.error = "*Invalid phone number."
            }
            else {
                binding.txtPhoneLayout.isErrorEnabled = false
            }

            return false

        }

        return true
    }

    private fun isValidFName(name: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        val PASSWORD_PATTERN = "[a-zA-Z].{1,30}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(name)

        return matcher.matches()

    }

    private fun isValidLName(name: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        val PASSWORD_PATTERN = "[a-zA-Z].{1,50}\$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(name)

        return matcher.matches()

    }

    private fun isValidPhoneNo(phone: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        val PASSWORD_PATTERN = "[0-9].{1,11}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(phone)

        return matcher.matches()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}
