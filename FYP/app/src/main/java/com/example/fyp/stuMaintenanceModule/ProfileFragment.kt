package com.example.fyp.stuMaintenanceModule


import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.text.method.TransformationMethod
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Notification
import com.example.fyp.Class.User
import com.example.fyp.Class.UserReview
import com.example.fyp.FirestoreAdapter.NotificationFirestoreAdapter
import com.example.fyp.FirestoreAdapter.UserProfileReviewRecycleView
import com.example.fyp.Interface.OnRatingClick
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentProfileBinding
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_rating.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), OnRatingClick {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserProfileReviewRecycleView
    private lateinit var registration: ListenerRegistration

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

        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            this.findNavController().navigate(R.id.fragment_home)
        }

        binding.btnDownArrow.setOnClickListener{
            if(expandableRated.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility = View.VISIBLE
                btnDownArrow.visibility = View.GONE
                btnUpArrow.visibility =View.VISIBLE
                iniRecycleView()
            } else {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility= View.GONE
                btnDownArrow.visibility = View.VISIBLE
                btnUpArrow.visibility =View.GONE
                iniRecycleView()
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
                iniRecycleView()
            } else {
                TransitionManager.beginDelayedTransition(expandableCard, AutoTransition())
                expandableRated.visibility= View.VISIBLE
                btnDownArrow.visibility = View.GONE
                btnUpArrow.visibility =View.VISIBLE
                iniRecycleView()
            }
        }

        intiUI()
//        iniRecycleView()

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

    private fun iniRecycleView() {
        val db = FirebaseFirestore.getInstance()

        try {
            val query = db.collection("User").document(userViewModel.user?.email!!)
                .collection("User_Review")
                .orderBy("reviewDate", Query.Direction.ASCENDING)

            registration = query.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val adapter = snapshot.toObjects(UserReview::class.java)
                    val adapter2 = UserProfileReviewRecycleView(this)
                    val manager = GridLayoutManager(activity, 2)

                    binding.rvRatedFood.layoutManager = manager
                    binding.rvRatedFood.adapter = adapter2
                    adapter2.data = adapter

                }
            }
        } catch (e: Exception) {

        }
    }

    private fun showRatingDialogue(review : UserReview){
        val dialog = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

        dialog.setView(dialogView)
        dialog.setCancelable(true)
        val image = dialogView.findViewById<ImageView>(R.id.imgFood)
        val a = FirebaseStorage.getInstance().getReference(review.imageUrl!!)

        // setup dialog view layout
        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }

        val z = dialog.show()

        dialogView.findViewById<TextView>(R.id.txtFoodName).text =
            review.foodName!!
        dialogView.findViewById<TextView>(R.id.txtFoodName).paintFlags =
            (dialogView.findViewById<TextView>(R.id.txtFoodName).paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        dialogView.ratingBar.rating = review.star!!

        dialogView.findViewById<Button>(R.id.btnConfirm)
            .setOnClickListener {
                val rating = dialogView.findViewById<RatingBar>(R.id.ratingBar).rating
                updateRating(rating, review)
                z.dismiss()
            }

    }

    private fun updateRating(rating : Float, review: UserReview) {
        val db = FirebaseFirestore.getInstance()

        db.runBatch {
            it.update(db.collection("User").document(userViewModel.user?.email!!)
                .collection("User_Review").document(review.id!!), "star", rating)

            it.update(db.collection("Canteen").document(review.canteen!!)
                .collection("Store").document(review.store!!)
                .collection("Food").document(review.foodName!!)
                .collection("Review").document(userViewModel.user?.email!!)
            ,"star", rating)
        }.addOnFailureListener {
            Log.i("Test", "Fail Update Rating")
            Log.i("Test", it.toString())
        }

    }

    override fun OnRatingItemClick(review: UserReview) {
        showRatingDialogue(review)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onPause() {
        super.onPause()
        registration.remove()
    }

    override fun onResume() {
        super.onResume()
        iniRecycleView()
    }
}
