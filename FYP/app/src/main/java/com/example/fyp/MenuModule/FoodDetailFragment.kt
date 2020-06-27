package com.example.fyp.MenuModule


import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.FirestoreAdapter.catAdapter
import com.example.fyp.MainActivity
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentFoodDetailBinding
import com.google.firebase.storage.FirebaseStorage
import android.text.style.UnderlineSpan
import android.text.SpannableString



/**
 * A simple [Fragment] subclass.
 */
class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private lateinit var viewModel: CanteenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_food_detail, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)


        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        binding.imgCart.setOnClickListener{
            it.requestFocus()
            it.findNavController()
                .navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToCartFragment())
        }

        binding.imgStar.setOnClickListener{
            val dialog = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(com.example.fyp.R.layout.fragment_rating, null)

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.show()
        }

        binding.btnAddToCart.setOnClickListener{
            it.findNavController()
                .navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToAddToCartFragment())
        }

        return binding.root
    }

    private fun intiUI() {
        binding.txtFood.text = viewModel.food.food_name
        binding.txtFood.setPaintFlags( binding.txtFood.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        binding.txtFoodDesc.text = viewModel.food.recipe_info
        binding.txtLocation.text = viewModel.canteen.type
        binding.txtStoreName.text = viewModel.store.store_name
        binding.txtReview.text = "( " + viewModel.food.total_review.toString() + " review)"
        binding.txtSmallPrice.text = "RM: " + viewModel.food.price.toString()
        binding.txtLargePrice.text = "RM: " + viewModel.food.price.toString()

        val adapter = catAdapter(viewModel.food.category)
        binding.rvCat.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.rvCat.adapter = adapter

        val image = binding.canteenImage
        val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)
        viewModel.setImage(image, a)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

  //custom dialog use in delete pop up message
//    fun dialog(){
//        val dialog = AlertDialog.Builder(activity)
//        val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)
//
//        dialog.setView(dialogView)
//        dialog.setCancelable(false)
//        dialog.show()

//        val customDialog = dialog.create()
//        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
//
//        })

//    }


}
