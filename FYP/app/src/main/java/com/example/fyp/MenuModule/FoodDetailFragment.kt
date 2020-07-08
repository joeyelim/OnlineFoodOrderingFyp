package com.example.fyp.MenuModule


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.CanteenReview
import com.example.fyp.Class.UserReview
import com.example.fyp.FirestoreAdapter.catAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentFoodDetailBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_rating.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private lateinit var viewModel: CanteenViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_food_detail, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)


        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        binding.imgCart.setOnClickListener {
            it.requestFocus()

            if (Firebase.auth.currentUser != null) {
                it.findNavController()
                    .navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToCartFragment())
            } else {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Oops, sorry!")
                dialog.setMessage("You Need to Login To View Cart")
                dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
                dialog.show()
            }

        }

        viewModel.rating.observe(this, Observer {
            binding.ratingBar2.rating = it
        })

        binding.imgStar.setOnClickListener {
            if (Firebase.auth.currentUser != null) {
                val dialog = AlertDialog.Builder(activity)
                val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

                dialog.setView(dialogView)
                dialog.setCancelable(true)
                val image = dialogView.findViewById<ImageView>(R.id.imgFood)
                val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)

                // setup dialog view layout
                viewModel.setImage(image, a)
                dialogView.findViewById<TextView>(R.id.txtFoodName).text =
                    viewModel.food.food_name!!
                dialogView.findViewById<TextView>(R.id.txtFoodName).paintFlags =
                    (dialogView.findViewById<TextView>(R.id.txtFoodName).paintFlags or Paint.UNDERLINE_TEXT_FLAG)

                dialogView.ratingBar.rating = viewModel.userRating!!

                dialogView.findViewById<Button>(R.id.btnConfirm)
                    .setOnClickListener {
                        val rating = dialogView.findViewById<RatingBar>(R.id.ratingBar).rating

                        viewModel.setRating(rating)
                        viewModel.newRating = rating

                        updateDatabase(rating)
                    }
                dialog.show()
            } else {
                val dialog = AlertDialog.Builder(context)

                dialog.setTitle("Oops, sorry!")
                dialog.setMessage("You Need to Login To Rate Food")
                dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
                dialog.show()
            }
        }

        binding.btnAddToCart.setOnClickListener {
            if (Firebase.auth.currentUser != null) {
                it.findNavController()
                    .navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToAddToCartFragment())
            } else {
                val dialog = AlertDialog.Builder(context)

                dialog.setTitle("Oops, sorry!")
                dialog.setMessage("You Need to Login Add Food Into Cart")
                dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
                dialog.show()
            }
        }

        return binding.root
    }

    private fun updateDatabase(rating: Float) {
        val calForDate = Calendar.getInstance().time
        val currentTime = SimpleDateFormat("HH:ss").format(calForDate)
        val currentDate = SimpleDateFormat("dd.MM.yyyy").format(calForDate)
        val userReviewId =
            viewModel.canteen.canteen_name + viewModel.store.store_name + viewModel.food.food_name

        val userReview: UserReview = UserReview(
            rating, currentTime, currentDate, viewModel.food.food_name,
            viewModel.food.food_image, userReviewId
        )
        val canteenReview: CanteenReview =
            CanteenReview(rating, currentTime, currentDate, userViewModel.user?.email)

        val db = FirebaseFirestore.getInstance()

        val oldStar = viewModel.userRating
        var newTotal = viewModel.food.total_review

        if (viewModel.gotReviewed) {
            val newStar = viewModel.food.total_star!! - oldStar!!.toFloat() + rating
            Log.i("Test", newStar.toString())

            db.runBatch {
                it.update(
                    db.collection("User").document(userViewModel.user?.email!!)
                        .collection("User_Review").document(userReviewId), "star", rating
                )

                it.update(
                    db.collection("Canteen").document(viewModel.canteen.type!!)
                        .collection("Store").document(viewModel.store.id!!)
                        .collection("Food").document(viewModel.food.food_name!!)
                        .collection("Review").document(userViewModel.user?.email!!), "star", rating
                )

                it.update(
                    db.collection("Canteen").document(viewModel.canteen.type!!)
                        .collection("Store").document(viewModel.store.id!!)
                        .collection("Food").document(viewModel.food.food_name!!),
                    "total_star",
                    newStar
                )

                updateTextView(newStar, newTotal, true)
            }
                .addOnCompleteListener {
                    viewModel.userRating = viewModel.newRating
                }

        } else {
            newTotal = newTotal?.plus(1)
            val newStar = viewModel.food.total_star?.plus(rating)

            db.runBatch {
                it.set(
                    db.collection("User").document(userViewModel.user?.email!!)
                        .collection("User_Review").document(userReviewId), userReview
                )

                it.set(
                    db.collection("Canteen").document(viewModel.canteen.type!!)
                        .collection("Store").document(viewModel.store.id!!)
                        .collection("Food").document(viewModel.food.food_name!!)
                        .collection("Review").document(userViewModel.user?.email!!), canteenReview
                )

                it.update(
                    db.collection("Canteen").document(viewModel.canteen.type!!)
                        .collection("Store").document(viewModel.store.id!!)
                        .collection("Food").document(viewModel.food.food_name!!),
                    "total_star",
                    newStar
                )

                it.update(
                    db.collection("Canteen").document(viewModel.canteen.type!!)
                        .collection("Store").document(viewModel.store.id!!)
                        .collection("Food").document(viewModel.food.food_name!!),
                    "total_review",
                    newTotal
                )

                updateTextView(newStar, newTotal, true)

            }.addOnCompleteListener {
                viewModel.userRating = viewModel.newRating
            }
        }


    }

    private fun updateTextView(star: Float?, total: Int?, reviewed: Boolean) {
        val average: Float = (star!! / total!!.toFloat())

        binding.txtReview.text = "( " + viewModel.food.total_review.toString() + " reviews ; " +
                "Average : " +
                DecimalFormat("#.#").format(average) + " stars )"
    }

    private fun intiUI() {
        val dec = DecimalFormat("RM ###.00")
        val average: Float =
            (viewModel.food.total_star!!.toFloat() / viewModel.food.total_review!!.toFloat())

        binding.txtFood.text = viewModel.food.food_name
        binding.txtFood.paintFlags = binding.txtFood.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.txtFoodDesc.text = viewModel.food.recipe_info
        binding.txtLocation.text = viewModel.canteen.type
        binding.txtStoreName.text = viewModel.store.store_name
        binding.txtReview.text = "( " + viewModel.food.total_review.toString() + " reviews ; " +
                "Average : " +
                DecimalFormat("#.#").format(average) + " stars )"
        binding.txtSmallPrice.text = dec.format(viewModel.food.small_price).toString()
        binding.txtLargePrice.text = dec.format(viewModel.food.large_price).toString()

        val adapter = catAdapter(viewModel.food.category)
        binding.rvCat.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.rvCat.adapter = adapter

        val image = binding.canteenImage
        val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)
        viewModel.setImage(image, a)

        viewModel.getUserRating(userViewModel.user?.email!!)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}
