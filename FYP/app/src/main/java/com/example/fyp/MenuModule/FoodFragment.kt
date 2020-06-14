package com.example.fyp.MenuModule


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Food
import com.example.fyp.FirestoreAdapter.FoodFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick2
import com.example.fyp.MainActivity
import com.example.fyp.R
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_food, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        Log.i("123","123")
        initRecycleView()


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    fun initRecycleView() {
        Log.i("123","123")
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Canteen").document("Canteen1")
            .collection("Store").document("Noodle").collection("Food")
            .orderBy("food_name", Query.Direction.ASCENDING)

        val options =
            FirestoreRecyclerOptions.Builder<Food>()
                .setQuery(query, Food::class.java).build()


        adapter = FoodFirestoreAdapter(options, this, context!!)
        binding.gvFoods.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.gvFoods.adapter = adapter

    }

    override fun onItemClick(food: Food, position: Int) {

//        this.findNavController()
//            .navigate()

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
