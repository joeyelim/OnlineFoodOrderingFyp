package com.example.fyp.OrderingModule


import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentOrderDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 */
class OrderDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_order_details, container, false
        )

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        cartViewModel = ViewModelProviders.of(activity!!).get(CartViewModel::class.java)


        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        return binding.root
    }

    private fun intiUI() {
        val dec = DecimalFormat("RM ###.00")

        binding.txtOrderDetailsDate.text = userViewModel.order.pickUp_Date
        binding.txtPickupTime.text = userViewModel.order.pickUp_Time
        binding.txtCanteenName.text = userViewModel.order.canteen_Name
        binding.txtStoreName.text = userViewModel.order.store_Name
        binding.txtQty.text = userViewModel.order.quantity.toString()
        binding.txtUnitPrice.text = dec.format(userViewModel.order.each_Price).toString()
        binding.txtDiningOption.text = userViewModel.order.dining_option
        binding.txtRemark.text = userViewModel.order.remark
        binding.txtProgress.text = userViewModel.order.status
        binding.txtPrice.text = dec.format(userViewModel.order.each_Price!!
                                    * userViewModel.order.quantity!!).toString()

        val image = binding.imageView3
        val a = FirebaseStorage.getInstance().getReference(userViewModel.order.imageUrl!!)

        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .fit()
                .into(image)
        }
        

//        FirebaseFirestore.getInstance()
//            .collection("Canteen").document(userViewModel.order.canteen_Name!!)
//            .collection("Store").document(userViewModel.order.store_Name!!)
//            .collection("Food")
//            .whereEqualTo("food_name", userViewModel.order.food_Name)
//            .get()
//            .addOnSuccessListener {
//                for (item in it.documents) {
//                    binding.txtFoodName.text = item["food_name"].toString()
//                    binding.txtFoodName.paintFlags = binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG
//                    binding.txtFoodDesc.text = userViewModel.food.recipe_info
//                    binding.txtLocation.text = userViewModel.canteen.type
//                    binding.txtStoreName.text = userViewModel.store.store_name
//                    binding.txtReview.text = "( " + userViewModel.food.total_review.toString() + " reviews ; " +
//                            "Average : " +
//                            DecimalFormat("#.#").format(average) + " stars )"
//                    binding.txtSmallPrice.text = dec.format(cartViewModel.food.small_price).toString()
//                    binding.txtLargePrice.text = dec.format(cartViewModel.food.large_price).toString()
//                }
//            }
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}
