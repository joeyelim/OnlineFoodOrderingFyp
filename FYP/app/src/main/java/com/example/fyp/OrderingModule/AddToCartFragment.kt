package com.example.fyp.OrderingModule


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Cart
import com.example.fyp.FirestoreAdapter.catAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentAddToCartBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat
import com.example.fyp.Class.Ultility.Companion.openDialog
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.fragments.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.fragment_food_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 */
class AddToCartFragment : Fragment() {
    private lateinit var binding: FragmentAddToCartBinding
    private lateinit var viewModel: CanteenViewModel
    private lateinit var userViewModel: UserViewModel
    var counter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_to_cart, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        binding.btnPlus.setOnClickListener{
            addCount()
        }

        binding.btnMinus.setOnClickListener {
            minusCount()
        }

        binding.btnAddToCart.setOnClickListener(View.OnClickListener {
            addingToCartList()
        })

        return binding.root
    }

    private fun addCount()
    {
        val quantity = binding.quantity
        val totalStock = viewModel.food.total_stock!!

        if (counter >= totalStock) {
            // custom dialog
            openDialog(activity!!)
            quantity.text = totalStock.toString()
        }
        else {
            counter++
            quantity.text = "$counter"
        }
    }

    private fun minusCount()
    {
        val quantity = binding.quantity

        if (counter <= 1){
            quantity.text = "1"
        }
        else{
            counter--
            quantity.text = "$counter"
        }
    }

    private fun getRadiobtnValue():Double
    {
        var priceEach = 0.00
        val radio = binding.radioGroup.checkedRadioButtonId
        val radioString = view!!.context.resources.getResourceEntryName(radio)
            if (radioString == "radio_button_1")
            {
                priceEach = viewModel.food.large_price.toString().toDouble()
            }
            else
            {
                priceEach = viewModel.food.small_price.toString().toDouble()
            }
        return priceEach
    }

    private fun addingToCartList()
    {
        // save cart id
        val calForDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val saveCurrentDateTime = formatter.format(calForDate)

        val db = FirebaseFirestore.getInstance()

        val foodName = viewModel.food.food_name.toString()
        val canteenName = viewModel.canteen.type.toString()
        val storeName = viewModel.store.store_name.toString()
        val quantity = binding.quantity.text.toString().toInt()
        val remark = binding.txtRemarks.text.toString()
        val image = viewModel.food.food_image.toString()
        val price = getRadiobtnValue()

        val cartList = ArrayList<Cart>()
        cartList.add(
            Cart(
                foodName, price , quantity, remark,
                canteenName, storeName, saveCurrentDateTime, image
            )
        )

        // upload item into cart
        db.runBatch {
            for ((index, item) in cartList.withIndex()) {
                it.set(db.collection("User").document(userViewModel.user?.email!!)
                    .collection("Cart").document(item.cart_ID!!), item)
            }

        }.addOnCompleteListener {
            if (it.isSuccessful) {
                showSnackBar()
            }
        }


    }

    private fun intiUI() {

        val dec = DecimalFormat("RM ###.00")

        binding.txtFoodName.setPaintFlags(binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        binding.txtCanteenName.text = viewModel.canteen.type
        binding.txtStoreName.text = viewModel.store.store_name
        binding.txtFoodName.text = viewModel.food.food_name
        binding.txtStockQty.text = viewModel.food.total_stock.toString()
        binding.txtSmallPrice.text = dec.format(viewModel.food.small_price).toString()
        binding.txtLargePrice.text = dec.format(viewModel.food.large_price).toString()

        val adapter = catAdapter(viewModel.food.category)
        binding.rvCat.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.rvCat.adapter = adapter

        val image = binding.imgFood
        val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)
        viewModel.setImage(image, a)

    }


    private fun showSnackBar()
    {
        val snackbar = Snackbar.make(
            clAddToCart, "Successfully Added into the Cart!", Snackbar.LENGTH_LONG
        )
        (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        snackbar.setAction("View Cart") {
            this.findNavController()
                .navigate(R.id.cartFragment)
        }
        snackbar.show()
    }



    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}
