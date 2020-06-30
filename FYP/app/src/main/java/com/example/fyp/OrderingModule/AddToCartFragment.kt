package com.example.fyp.OrderingModule


import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.FirestoreAdapter.catAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentAddToCartBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat



/**
 * A simple [Fragment] subclass.
 */
class AddToCartFragment : Fragment() {
    private lateinit var binding: FragmentAddToCartBinding
    private lateinit var viewModel: CanteenViewModel
    var counter = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_to_cart, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnPlus.setOnClickListener {
            counter++
            val quantity = binding.quantity
            val totalStock = viewModel.food.total_stock!!
            quantity.text = "$counter"

            if (counter > totalStock) {
               // custom dialog use in delete pop up message
                Toast.makeText(activity, "exceed total stock $totalStock",Toast.LENGTH_SHORT).show()

            }

        }

        binding.btnMinus.setOnClickListener {
            counter--

            val quantity = binding.quantity
            quantity.text = "$counter"

//            if (counter < 2) {
//                buttonDisable()
//                counter = 1
//                quantity.text = "$counter"
//            }


        }


        intiUI()

        return binding.root
    }

    private fun intiUI() {
        val dec = DecimalFormat("RM ###.00")

        binding.txtCanteenName.text = viewModel.canteen.type
        binding.txtStoreName.text = viewModel.store.store_name
        binding.txtFoodName.text = viewModel.food.food_name
        binding.txtFoodName.setPaintFlags( binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

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

    fun buttonDisable(){
        binding.btnMinus.isEnabled = false
    }



    fun dialog(){
        val dialog = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.show()

        val customDialog = dialog.create()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({

        })

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}
