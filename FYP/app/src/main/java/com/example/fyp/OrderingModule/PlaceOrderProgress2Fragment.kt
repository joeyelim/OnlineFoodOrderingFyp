package com.example.fyp.OrderingModule


import android.os.Bundle
import android.util.Log
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
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Order_Food
import com.example.fyp.FirestoreAdapter.PlaceOrderItemAdapter
import com.example.fyp.FirestoreAdapter.TestAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentPlaceOrderProgress2Binding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.outer_recycle_view_layout.view.*
import java.text.DecimalFormat

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

//            it.findNavController()
//                .navigate(PlaceOrderProgress2FragmentDirections
//                    .actionPlaceOrderProgress2FragmentToPlaceOrderProgress3Fragment())
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

        return binding.root
    }

    private fun initUI() {
        binding.txtDate.text = cartViewModel.order.pickUp_Date
        binding.txtPickupTime.text = cartViewModel.order.pickUp_Time
        binding.txtOption.text = cartViewModel.order.dining_Option
        binding.txttotalPrice.text = cartViewModel.order.total_Price.toString()
        binding.txttotalPrice.text = DecimalFormat("RM ###.00")
            .format(cartViewModel.order.total_Price).toString()
    }

    private fun initRecycleView() {
        val adapter = getCartArrayList()
        val adapter2 = PlaceOrderItemAdapter()
        binding.orderFoodList.setHasFixedSize(true)
        binding.orderFoodList.layoutManager = LinearLayoutManager(context)
        binding.orderFoodList.adapter = adapter2
        adapter2.data = adapter
    }

    private fun getCartArrayList() : ArrayList<Cart> {
        val cartArrayList = ArrayList<Cart>()
        cartArrayList.addAll(cartViewModel.hashMap.values)

        return cartArrayList
    }

    private fun updateDatabase(view : View) {
        cartViewModel.initOrderFoodList()

        val db = FirebaseFirestore.getInstance()

        Toast.makeText(activity, "Processing Your Order ... ", Toast.LENGTH_LONG).show()

        // batch write : do write at once, make sure all completed den navigate to next page
        db.runBatch {

            // write into user -> Order
            it.set(db.collection("User").document(userViewModel.user?.email!!)
                .collection("Order").document(cartViewModel.order.id!!),
                cartViewModel.order)

            // write into user -> Order -> Order_Food
            for (item in cartViewModel.orderFood) {
                it.set(db.collection("User").document(userViewModel.user?.email!!)
                    .collection("Order").document(cartViewModel.order.id!!)
                    .collection("Order_Food").document(item.id!!)
                , item)
            }

            // write into Canteen -> Store -> Order
            val canteenStoreList = ArrayList<String>()

            for (item in cartViewModel.orderFood) {
                val canteenStore = item.canteen_Name + item.store_Name

                if (!canteenStoreList.contains(canteenStore)) {
                    canteenStoreList.add(canteenStore)
                    it.set(db.collection("Canteen").document(item.canteen_Name!!)
                        .collection("Store").document(item.store_Name!!)
                        .collection("Order").document(cartViewModel.order.id!!),
                        cartViewModel.order)
                }
            }

            // Write into Canteen -> Store -> Order
            for (item in cartViewModel.orderFood) {
                it.set(db.collection("Canteen").document(item.canteen_Name!!)
                    .collection("Store").document(item.store_Name!!)
                    .collection("Order").document(cartViewModel.order.id!!)
                    .collection("Order_Food").document(item.id!!)
                    ,item)
            }

            // Delete Existing Cart
            cartViewModel.hashMap.forEach{(key, value) ->
                    it.delete(db.collection("User").document(userViewModel.user?.email!!)
                        .collection("Cart").document(value.cart_ID!!))
            }

        }.addOnCompleteListener {
            Log.i("Test", "Write Complete")
            Toast.makeText(activity, "Order Being Placed! ", Toast.LENGTH_LONG).show()
            cartViewModel.removeAll()
            view.findNavController()
                .navigate(PlaceOrderProgress2FragmentDirections
                    .actionPlaceOrderProgress2FragmentToPlaceOrderProgress3Fragment())
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}
