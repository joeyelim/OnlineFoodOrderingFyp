package com.example.fyp.OrderingModule


import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentOrderDetailsBinding
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
//        val dec = DecimalFormat("RM ###.00")
//
//        binding.txtOrderDetailsDate.text = userViewModel.order.pickUp_Date
//        binding.txtPickupTime.text = userViewModel.order.pickUp_Time
//        binding.txtCanteenName.text = userViewModel.order.canteen_Name
//        binding.txtStoreName.text = userViewModel.order.pickUp_Time
//        binding.txtQty.text = userViewModel.order.pickUp_Time
//        binding.txtUnitPrice.text = userViewModel.order.pickUp_Time
//        binding.txtDiningOption.text = userViewModel.order.pickUp_Time
//        binding.txtRemark.text = userViewModel.order.pickUp_Time
//        binding.txtProgress.text = userViewModel.order.pickUp_Time
//        binding.txtPrice.text = userViewModel.order.pickUp_Time
//
//            binding.txtFoodName.text = userViewModel.food.food_name
//        binding.txtFoodName.paintFlags = binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG
//
//        binding.txtFoodDesc.text = userViewModel.food.recipe_info
//        binding.txtLocation.text = userViewModel.canteen.type
//        binding.txtStoreName.text = userViewModel.store.store_name
//        binding.txtReview.text = "( " + userViewModel.food.total_review.toString() + " reviews ; " +
//                "Average : " +
//                DecimalFormat("#.#").format(average) + " stars )"
//        binding.txtSmallPrice.text = dec.format(cartViewModel.food.small_price).toString()
//        binding.txtLargePrice.text = dec.format(cartViewModel.food.large_price).toString()


    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}
