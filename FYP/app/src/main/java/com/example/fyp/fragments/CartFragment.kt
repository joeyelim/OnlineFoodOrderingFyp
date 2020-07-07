package com.example.fyp.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Food
import com.example.fyp.Interface.OnAdapterItemClick
import com.example.fyp.FirestoreAdapter.CartFirestoreAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentCartBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment(), OnAdapterItemClick {

    private lateinit var binding: FragmentCartBinding
    private var adapter: CartFirestoreAdapter? = null
    private lateinit var viewModel: CanteenViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var cartViewModel: CartViewModel
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
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        cartViewModel = ViewModelProviders.of(activity!!).get(CartViewModel::class.java)


        Log.i("Test", cartViewModel.activeButton.value.toString())

        checkLogin()
        initRecycleView()

//        binding.btnCheckOut.setOnClickListener {
//            if (!it.isEnabled) {
//                Snackbar.make(
//                    cartLayout, "Please select at least one food before you proceed to checkout!", Snackbar.LENGTH_LONG
//                ).show()
//            } else
//            {
//                it.findNavController()
//                    .navigate(CartFragmentDirections.actionCartFragmentToPlaceOrderFragment())
//            }
//        }

        binding.btnViewShop.setOnClickListener {
            it.findNavController()
                .navigate(CartFragmentDirections.actionCartFragmentToFragmentHome())
        }

        //-------------------Firebase storage-----------------------

        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference

        var imagesRef: StorageReference? =
            storageRef.child("gs://fypfirebaseproject-fd994.appspot.com")

        // Child references can also take paths
        // spaceRef now points to "images/space.jpg
        // imagesRef still points to "images"
        var spaceRef = storageRef.child("canteen1.jpg")

        // binding.imageView2.setImageResource(spaceRef)

        return binding.root
    }

    private fun checkLogin() {
        if (Firebase.auth.currentUser == null || userViewModel.user?.email == "") {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToFragmentHome())
        }
    }

    private fun initRecycleView() {

        val db = FirebaseFirestore.getInstance()

        try {
            val query = db.collection("User").document(userViewModel.user?.email!!)
                .collection("Cart")
                .orderBy("cart_ID", Query.Direction.ASCENDING)

            query.addSnapshotListener { p0, _ ->
                if (p0 != null) {

                    if(p0.size() > 0) {
                        binding.emptyLayout.visibility = View.GONE;
                        binding.cartLayout.visibility = View.VISIBLE
                    }else {
                        binding.emptyLayout.visibility = View.VISIBLE;
                        binding.cartLayout.visibility = View.GONE
                    }
                }
            }

            val options =
                FirestoreRecyclerOptions.Builder<Cart>()
                    .setQuery(query, Cart::class.java).build()


            adapter = CartFirestoreAdapter(options, this, context!!)
            binding.rcCart.layoutManager = LinearLayoutManager(activity)
            binding.rcCart.adapter = adapter

        }
        catch (e: Exception) {
            return
        }
    }

    private fun observeCartButton() {
        cartViewModel.activeButton.observe(this, Observer {
            if (it > 0) {
                binding.btnCheckOut.setTextColor(resources.getColor(R.color.white))
                binding.btnCheckOut.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                binding.btnCheckOut.isEnabled = true

                binding.btnCheckOut.setOnClickListener {view ->
                    view.findNavController()
                        .navigate(CartFragmentDirections.actionCartFragmentToPlaceOrderFragment())
                }

            } else {
                binding.btnCheckOut.setTextColor(resources.getColor(R.color.white))
                binding.btnCheckOut.setBackgroundColor(resources.getColor(R.color.lightGray))
//                binding.btnCheckOut.isEnabled = false
                binding.btnCheckOut.setOnClickListener {
                    Snackbar.make(
                        cartLayout, "Please select at least one food before you proceed to checkout!", Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun addBtnClick(cart: Cart, view: TextView, txt: TextView) {
        FirebaseFirestore.getInstance()
            .collection("Canteen").document(cart.canteen_name!!)
            .collection("Store").document(cart.store_name!!)
            .collection("Food").document(cart.food_name!!)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var counter = (view.text).toString().toInt()
                    val price = cart.each_price
                    val food = it.result?.toObject(Food::class.java)

                    if (counter >= food?.total_stock!!) {
                        // custom dialog
                        openDialog()
                        counter = food.total_stock!!
                    } else {
                        counter++
                    }
                    view.text = "$counter"
                    txt.text = DecimalFormat("RM ###.00").format(counter * price!!).toString()
                }
            }
    }

    override fun minusBtnClick(cart: Cart, view: TextView, txt: TextView) {
        var counter = (view.text).toString().toInt()
        val price = cart.each_price

        if (counter <= 1) {
            counter = 1

        } else {
            counter--
        }

        view.text = "$counter"
        txt.text = DecimalFormat("RM ###.00").format(counter * price!!).toString()
    }

    override fun deleteBtnClick(cart: Cart) {
        delDialog(cart)
    }

    override fun checkBoxClick(cart: Cart, checkBox: CheckBox) {
        if (checkBox.isChecked) {
            cartViewModel.activateCartButton()
            cartViewModel.addItem(cart)
        } else {
            cartViewModel.deActivateCartButton()
            cartViewModel.removeItem(cart)
        }
    }

    private fun delDialog(cart: Cart) {
        val dialog = AlertDialog.Builder(activity)
        val foodName: String? = cart.food_name

        dialog.setTitle("Confirmation")
        dialog.setMessage(
            "Are you sure want to delete the order?" +
                    "You cannot undo this action! \n* $foodName"
        )
        dialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            FirebaseFirestore.getInstance()
                .collection("User").document(userViewModel.user?.email!!)
                .collection("Cart").document(cart.cart_ID!!)
                .delete()
                .addOnCompleteListener {
                    val snackbar = Snackbar.make(
                        this.requireView(),
                        "Item Being Deleted",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("Close") {
                        snackbar.dismiss()
                    }
                    snackbar.show()
                }
        }
        dialog.setNegativeButton("No") { _: DialogInterface, _: Int -> }
        dialog.show()
    }

    private fun openDialog() {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle("Oops, sorry!")
        dialog.setMessage("Your order quantity has exceeded the maximum inventory, please select again.")
        dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
        dialog.show()
    }

    override fun onStart() {
        super.onStart()

        if (userViewModel.user!!.email != "") {
            adapter?.startListening()
        }
    }

    override fun onResume() {
        super.onResume()
        observeCartButton()
    }

    override fun onStop() {
        super.onStop()

        cartViewModel.activeButton.removeObservers(this)

        if (userViewModel.user!!.email != "") {
            adapter?.stopListening()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cartViewModel.resetCartButton()
    }


}
