package com.example.fyp.OrderingModule


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fyp.Class.Canteen
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentPlaceOrderProgress2Binding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class PlaceOrderProgress2Fragment : Fragment() {
    private lateinit var binding: FragmentPlaceOrderProgress2Binding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_order_progress2, container, false
        )

        cartViewModel = ViewModelProviders.of(activity!!).get(CartViewModel::class.java)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnConfirm.setOnClickListener{
            updateDatabase()

//            it.findNavController()
//                .navigate(PlaceOrderProgress2FragmentDirections
//                    .actionPlaceOrderProgress2FragmentToPlaceOrderProgress3Fragment())
        }

        binding.btnBack.setOnClickListener{
            it.findNavController()
                .navigate(PlaceOrderProgress2FragmentDirections
                    .actionPlaceOrderProgress2FragmentToPlaceOrderFragment())
        }

        initUI()

        return binding.root
    }

    private fun initUI() {
        binding.txtDate.text = cartViewModel.order.pickUp_Date
        binding.txtPickupTime.text = cartViewModel.order.pickUp_Time
        binding.txtOption.text = cartViewModel.order.dining_Option
        binding.txttotalPrice.text = cartViewModel.order.total_Price.toString()
    }

    private fun updateDatabase() {
        cartViewModel.initOrderFoodList()


        val db = FirebaseFirestore.getInstance()
        db.collection("User").document(userViewModel.user?.email!!)
            .collection("Order").document(cartViewModel.order.id!!)
            .set(cartViewModel.order)
            .addOnSuccessListener {
                Log.i("Test", "success Order")
            }
            .addOnFailureListener {
                Log.i("Test", "Fail Order", it)
            }.addOnCompleteListener {
                for (item in cartViewModel.orderFood) {
                    db.collection("User").document(userViewModel.user?.email!!)
                        .collection("Order").document(cartViewModel.order.id!!)
                        .collection("Order_Food").document(item.food_Name!!)
                        .set(item)
                        .addOnSuccessListener {
                            Log.i("Test", "success Order_Food")
                        }
                        .addOnFailureListener {
                            Log.i("Test", "Fail Order_Food", it)
                        }
                }
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}
