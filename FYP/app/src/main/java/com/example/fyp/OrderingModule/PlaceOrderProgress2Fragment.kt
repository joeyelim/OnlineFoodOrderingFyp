package com.example.fyp.OrderingModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.FirestoreAdapter.PlaceOrderItemAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentPlaceOrderProgress2Binding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

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

        binding.btnConfirm.setOnClickListener {
            updateDatabase(it)
        }

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigate(
                    PlaceOrderProgress2FragmentDirections
                        .actionPlaceOrderProgress2FragmentToPlaceOrderFragment()
                )
        }

        initUI()
        initRecycleView()
        cartViewModel.initOrderFoodList(userViewModel.user?.email!!)

        return binding.root
    }

    private fun initUI() {
        val calForDate = Calendar.getInstance().time
        val date = SimpleDateFormat("dd.MM.yyyy").format(calForDate)
        binding.txtDate.text = date
        binding.txtPickupTime.text = cartViewModel.pickUpTime
        binding.txtOption.text = cartViewModel.option
        binding.txttotalPrice.text = cartViewModel.order.total_Price.toString()
        binding.txttotalPrice.text = DecimalFormat("RM ###.00")
            .format(cartViewModel.getTotalPrice()).toString()
    }

    private fun initRecycleView() {
        val adapter = cartViewModel.cartArrayList
        val adapter2 = PlaceOrderItemAdapter()
        binding.orderFoodList.setHasFixedSize(true)
        binding.orderFoodList.layoutManager = LinearLayoutManager(context)
        binding.orderFoodList.adapter = adapter2
        adapter2.data = adapter

    }

    private fun updateDatabase(view: View) {

        val db = FirebaseFirestore.getInstance()
        var quantity2 : Double = 0.00

        Toast.makeText(activity, "Processing Your Order ... ", Toast.LENGTH_LONG).show()

        // batch write : do write at once, make sure all completed den navigate to next page
        db.runBatch {

            for (item in cartViewModel.orderFood) {
                // Write into User -> Order_Food
                it.set(
                    db.collection("User").document(userViewModel.user?.email!!)
                        .collection("Order_Food").document(item.id!!)
                    , item
                )

                // Write into Canteen -> Store -> Order
                item.email = userViewModel.user?.email
                it.set(
                    db.collection("Canteen").document(item.canteen_Name!!)
                        .collection("Store").document(item.store_Name!!)
                        .collection("Order_Food").document(item.id!!)
                    , item
                )

                quantity2 = item.quantity?.toDouble()!!

                it.update(
                    db.collection("Canteen").document(item.canteen_Name!!)
                        .collection("Store").document(item.store_Name!!)
                        .collection("Food").document(item.food_Name!!)
                    , "total_stock", FieldValue.increment(-quantity2)
                )

            }

            // Delete Existing Cart
            for (item in cartViewModel.cartArrayList) {
                it.delete(
                    db.collection("User").document(userViewModel.user?.email!!)
                        .collection("Cart").document(item.cart_ID!!)
                )
            }

            // update Food Quantity


        }.addOnCompleteListener {
            Toast.makeText(activity, "Order Being Placed! ", Toast.LENGTH_LONG).show()
            cartViewModel.removeAll()
            view.findNavController()
                .navigate(
                    PlaceOrderProgress2FragmentDirections
                        .actionPlaceOrderProgress2FragmentToPlaceOrderProgress3Fragment()
                )
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}
