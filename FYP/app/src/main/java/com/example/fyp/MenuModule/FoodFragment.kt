package com.example.fyp.MenuModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Food
import com.example.fyp.FirestoreAdapter.FoodFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick2
import com.example.fyp.MainActivity
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentFoodBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * A simple [Fragment] subclass.
 */
class FoodFragment : Fragment(), onListClick2 {

    private lateinit var binding: FragmentFoodBinding
    private var adapter: FoodFirestoreAdapter? = null
    private lateinit var viewModel: CanteenViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_food, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)


        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        initRecycleView()

        binding.btnCart.setOnClickListener {
            it.requestFocus()
            it.findNavController()
                .navigate(FoodFragmentDirections.actionFoodFragmentToCartFragment())
        }

        updateUI()



        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun updateUI() {
        binding.txtStore.text = viewModel.store.store_name
    }

    fun initRecycleView() {
        val db = FirebaseFirestore.getInstance()
        val canteenType = viewModel.canteen.type!!
        val storeType = viewModel.store.id!!
        val query = db.collection("Canteen").document(canteenType)
            .collection("Store").document(storeType).collection("Food")
            .orderBy("food_name", Query.Direction.ASCENDING)

        val options =
            FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food::class.java).build()


        adapter = FoodFirestoreAdapter(options, this, context!!)
        binding.gvFoods.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.gvFoods.adapter = adapter

    }

    override fun onItemClick(food: Food, position: Int) {
        viewModel.food = food
        this.findNavController()
            .navigate(FoodFragmentDirections.actionFoodFragmentToFoodDetailFragment())
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
