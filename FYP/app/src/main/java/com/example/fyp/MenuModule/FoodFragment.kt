package com.example.fyp.MenuModule


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
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_order_list.*

/**
 * A simple [Fragment] subclass.
 */
class FoodFragment : Fragment(), onListClick2 {

    private lateinit var binding: FragmentFoodBinding
    private lateinit var adapter: FoodFirestoreAdapter
    private lateinit var viewModel: CanteenViewModel
    private lateinit var db : FirebaseFirestore
    private lateinit var canteenType : String
    private lateinit var storeType : String


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

        binding.btnCart.setOnClickListener {
            it.requestFocus()
            it.findNavController()
                .navigate(FoodFragmentDirections.actionFoodFragmentToCartFragment())
        }
        db = FirebaseFirestore.getInstance()
        canteenType = viewModel.canteen.type!!
        storeType = viewModel.store.id!!

        initRecycleView()
        initTab()
        updateUI()

        binding.FoodFilterTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(activity, (tab!!.text.toString().capitalize()), Toast.LENGTH_LONG).show()
                updateRecycleView((tab!!.text.toString()).capitalize())
            }

        })

        return binding.root
    }

    private fun initTab() {
        binding.FoodFilterTab.addTab(binding.FoodFilterTab.newTab().setText("all"))

        for (item in viewModel.store.category) {
            binding.FoodFilterTab.addTab(binding.FoodFilterTab.newTab().setText(item))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun updateUI() {
        binding.txtStore.text = viewModel.store.store_name

        val image = binding.canteenImage
        val a = FirebaseStorage.getInstance().getReference(viewModel.store.store_image!!)
        viewModel.setImage(image, a)

    }

    private fun initRecycleView() {
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

    private fun updateRecycleView(selected : String) {
        var newquery= db.collection("Canteen").document(canteenType)
            .collection("Store").document(storeType).collection("Food")
            .orderBy("food_name", Query.Direction.ASCENDING)

        if (selected != "All") {
            newquery = newquery.whereArrayContains("category", selected)
        }

        val newoptions =
            FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(newquery, Food::class.java).build()

        adapter.updateOptions(newoptions)
    }

    override fun onItemClick(food: Food, position: Int) {
        viewModel.food = food
        this.findNavController()
            .navigate(FoodFragmentDirections.actionFoodFragmentToFoodDetailFragment())
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()

        adapter.stopListening()
    }


}
