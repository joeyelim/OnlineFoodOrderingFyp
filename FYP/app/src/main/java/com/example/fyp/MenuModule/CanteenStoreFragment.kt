package com.example.fyp.MenuModule


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.CanteenStore
import com.example.fyp.FirestoreAdapter.CanteenFireStoreRecyclerAdapter
import com.example.fyp.FirestoreAdapter.StoreFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick
import com.example.fyp.FirestoreAdapter.onListClick1
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.databinding.FragmentCanteenStoreBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.canteen_row.view.*
import kotlinx.android.synthetic.main.fragment_canteen_store.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

/**
 * A simple [Fragment] subclass.
 */
class CanteenStoreFragment : Fragment(), onListClick1 {

    private lateinit var binding: FragmentCanteenStoreBinding
    private var adapter: StoreFirestoreAdapter? = null
    private lateinit var viewModel : CanteenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_canteen_store, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnCart.setOnClickListener{
            it.requestFocus()
            it.findNavController()
                .navigate(CanteenStoreFragmentDirections.actionCanteenStoreFragmentToCartFragment())
        }

        setUpUI()
        initRecycleView()


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun setUpUI() {
        binding.canteen.text = viewModel.canteen.type

        val image = binding.canteenImage
        val a = FirebaseStorage.getInstance().getReference(viewModel.canteen.image!!)
        viewModel.setImage(image, a)
    }

    fun initRecycleView() {
        Log.i("123","123")
        val canteenType = viewModel.canteen.type!!
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Canteen").document(canteenType)
            .collection("Store").orderBy("store_name", Query.Direction.ASCENDING)

        val options =
            FirestoreRecyclerOptions.Builder<CanteenStore>()
                .setQuery(query, CanteenStore::class.java).build()


        adapter = StoreFirestoreAdapter(options, this, context!!)
        binding.rvCanteenStore.layoutManager = LinearLayoutManager(activity)
        binding.rvCanteenStore.adapter = adapter

    }

    override fun onItemClick(store: CanteenStore, position: Int) {
        viewModel.store = store
        this.findNavController()
            .navigate(CanteenStoreFragmentDirections.actionCanteenStoreFragmentToFoodFragment())

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
