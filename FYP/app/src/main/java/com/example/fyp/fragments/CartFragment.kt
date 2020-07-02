package com.example.fyp.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Cart
import com.example.fyp.FirestoreAdapter.CartFirestoreAdapter
import com.example.fyp.FirestoreAdapter.FoodFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick3
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentCartBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment(), onListClick3 {

    private lateinit var binding: FragmentCartBinding
    private var adapter: CartFirestoreAdapter? = null
    private lateinit var viewModel : CanteenViewModel
    var counter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavVisible()

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)
        initRecycleView()



        //----------------------------------------------------------------------------------------

        val storage = Firebase.storage
        // Create a storage reference from our app
        var storageRef = storage.reference

        var imagesRef: StorageReference? = storageRef.child("gs://fypfirebaseproject-fd994.appspot.com")

// Child references can also take paths
// spaceRef now points to "images/space.jpg
// imagesRef still points to "images"
        var spaceRef = storageRef.child("canteen1.jpg")

//        binding.imageView2.setImageResource(spaceRef)

//---------------------------------------------------------------------------------------------------------------

        binding.btnCheckOut.setOnClickListener {
            it.findNavController()
                .navigate(CartFragmentDirections.actionCartFragmentToPlaceOrderFragment())
        }

        return binding.root
    }




    private fun initRecycleView() {

        val db = FirebaseFirestore.getInstance()

        val query = db.collection("User").document("limye-wm18@student.tarc.edu.my")
            .collection("Cart")
            .orderBy("cart_ID", Query.Direction.ASCENDING)

        val options =
            FirestoreRecyclerOptions.Builder<Cart>()
                .setQuery(query, Cart::class.java).build()


        adapter = CartFirestoreAdapter(options, this, context!!)
        binding.rcCart.layoutManager = LinearLayoutManager(activity)
        binding.rcCart.adapter = adapter

    }

    override fun onItemClick(cart: Cart, position: Int) {

    }


    fun delDialog(cart: Cart){
        val dialog = AlertDialog.Builder(activity)
        val foodName: String? = cart.food_name

        dialog.setTitle("Confirmation")
        dialog.setMessage("Are you sure want to delete the order?\n* $foodName")
        dialog.setPositiveButton("Yes") { _: DialogInterface, i: Int -> }
        dialog.setNegativeButton("No") { _: DialogInterface, i: Int -> }
        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter?.stopListening()
        }
    }


}
