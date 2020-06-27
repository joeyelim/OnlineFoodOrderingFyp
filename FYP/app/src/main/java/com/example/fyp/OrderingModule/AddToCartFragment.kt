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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.FirestoreAdapter.catAdapter
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentAddToCartBinding
import com.google.firebase.storage.FirebaseStorage

/**
 * A simple [Fragment] subclass.
 */
class AddToCartFragment : Fragment() {
    private lateinit var binding: FragmentAddToCartBinding
    private lateinit var viewModel: CanteenViewModel

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

        intiUI()

        return binding.root
    }


    private fun intiUI() {
        binding.txtCanteenName.text = viewModel.canteen.type
        binding.txtStoreName.text = viewModel.store.store_name
        binding.txtFoodName.text = viewModel.food.food_name
        binding.txtFoodName.setPaintFlags( binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        binding.txtStockQty.text = viewModel.food.total_stock.toString()
        binding.txtSmallPrice.text = "RM: " + viewModel.food.price.toString()
        binding.txtLargePrice.text = "RM: " + viewModel.food.price.toString()

        val adapter = catAdapter(viewModel.food.category)
        binding.rvCat.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.rvCat.adapter = adapter

        val image = binding.imgFood
        val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)
        viewModel.setImage(image, a)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}
