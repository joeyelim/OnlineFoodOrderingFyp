package com.example.fyp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.Fragment
import com.example.fyp.Class.Canteen
import com.example.fyp.MainActivity
import com.example.fyp.Model
import com.example.fyp.MyAdapter

import com.example.fyp.R
import com.example.fyp.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dataListener: ListenerRegistration

    companion object {

        private val TAG = "DocSnippets"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        (activity as MainActivity).setNavVisible()

//        binding.textView4.text = "123"
//        initializeFirebase()
        initRecycleView()

        return binding.root
    }

    fun initRecycleView() {
        val db = FirebaseFirestore.getInstance()

        // Query
        val query = db.collection(("Canteen"))

        query
            .get()
            .addOnSuccessListener { querySnapshot ->
                val adapter = querySnapshot.toObjects(Canteen::class.java)
                val adapter2 = HomeAdapter()
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter2
                adapter2.data = adapter
            }
    }

//    private fun initializeFirebase () {
////        val docRef = FirebaseFirestore.getInstance().collection("Canteen")
////            .whereEqualTo("type",1)
////            .get()
////
////        docRef.addOnSuccessListener {
////            for (document in it) {
////                Log.d(TAG,"${document.id} => ${document.data}")
////                binding.textView4.append(document["type"].toString())
////            }
////        }  .addOnFailureListener { exception ->
////            Log.w(TAG, "Error getting documents: ", exception)
////        }
//
////        docRef.get().addOnSuccessListener {
////            Log.w(TAG, "Listen Success.")
////            binding.textView4.append(it["type"].toString())
////        }.addOnFailureListener {
////            Log.w(TAG, "Listen failed.")
////        }
//
//        val docRef = FirebaseFirestore.getInstance().collection("Canteen")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null ) {
//                for (document in snapshot.documents)
//
//                binding.textView4.append(document["type"].toString())
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//
//
////            if (snapshot != null && snapshot.exists()) {
////                Log.d(TAG, "Current data: ${snapshot.data}")
////                binding.textView4.append(snapshot["type"].toString())
////            } else {
////                Log.d(TAG, "Current data: null")
////            }
//        }
//
//    }


}
