package com.example.fyp.stuMaintenanceModule


import android.os.Bundle
import android.text.method.TransformationMethod
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fyp.Class.User
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI.getApplicationContext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        setHasOptionsMenu(true)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        binding.btnEdit.setOnClickListener {
            it.findNavController()
                .navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }

        binding.btnChangePwd.setOnClickListener {
            it.findNavController()
                .navigate(ProfileFragmentDirections.actionProfileFragmentToChangePwdFragment())
        }

        binding.btnDownArrow.setOnClickListener{
            if(expandableRated.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility = View.VISIBLE
                btnDownArrow.visibility = View.GONE
                btnUpArrow.visibility =View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility= View.GONE
                btnDownArrow.visibility = View.VISIBLE
                btnUpArrow.visibility =View.GONE
            }
        }

        binding.btnDownArrow2.setOnClickListener{
            if(expandableInfo.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(personalInfo, AutoTransition())
                expandableInfo.visibility = View.VISIBLE
                btnDownArrow2.visibility = View.GONE
                btnUpArrow2.visibility =View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(personalInfo, AutoTransition())
                expandableInfo.visibility= View.GONE
                btnDownArrow2.visibility = View.VISIBLE
                btnUpArrow2.visibility =View.GONE
            }
        }

        binding.btnUpArrow2.setOnClickListener{
            if(expandableInfo.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(personalInfo, AutoTransition())
                expandableInfo.visibility = View.GONE
                btnDownArrow2.visibility = View.VISIBLE
                btnUpArrow2.visibility =View.GONE
            } else {
                TransitionManager.beginDelayedTransition(personalInfo, AutoTransition())
                expandableInfo.visibility= View.VISIBLE
                btnDownArrow2.visibility = View.GONE
                btnUpArrow2.visibility =View.VISIBLE
            }
        }

        binding.btnUpArrow.setOnClickListener{
            if(expandableRated.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility = View.GONE
                btnDownArrow.visibility = View.VISIBLE
                btnUpArrow.visibility =View.GONE
            } else {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility= View.VISIBLE
                btnDownArrow.visibility = View.GONE
                btnUpArrow.visibility =View.VISIBLE
            }
        }

        intiUI()

        (activity as MainActivity).setNavVisible()

        return binding.root
    }

    private fun intiUI() {
        binding.txtname.text = userViewModel.user?.first_name + " " + userViewModel.user?.last_name
        binding.txtemail.text = userViewModel.user?.email
        binding.txtFullName.text = userViewModel.user?.first_name + " " + userViewModel.user?.last_name
        binding.txtEmail.text = userViewModel.user?.email
        binding.txtPhone.text = userViewModel.user?.phone_number
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}
