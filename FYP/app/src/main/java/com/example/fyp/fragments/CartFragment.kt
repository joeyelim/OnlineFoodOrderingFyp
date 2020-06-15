package com.example.fyp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fyp.R
import com.example.fyp.databinding.FragmentCartBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false
        )



        val storage = Firebase.storage
        // Create a storage reference from our app
        var storageRef = storage.reference

        var imagesRef: StorageReference? = storageRef.child("gs://fypfirebaseproject-fd994.appspot.com")

// Child references can also take paths
// spaceRef now points to "images/space.jpg
// imagesRef still points to "images"
        var spaceRef = storageRef.child("canteen1.jpg")

//        binding.imageView2.setImageResource(spaceRef)


        binding.btnCheckOut.setOnClickListener {
            it.findNavController()
                .navigate(CartFragmentDirections.actionCartFragmentToPlaceOrderFragment())
        }

        return binding.root
    }


}
