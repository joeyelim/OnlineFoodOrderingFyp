package com.example.fyp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.FirestoreAdapter.CanteenFireStoreRecyclerAdapter
import com.example.fyp.FirestoreAdapter.onListClick
import com.example.fyp.Class.Canteen
import com.example.fyp.MainActivity
import com.example.fyp.databinding.FragmentHomeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query




/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), onListClick {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var dataListener: ListenerRegistration
    private var adapter: CanteenFireStoreRecyclerAdapter? = null
    private lateinit var mAuth : FirebaseAuth


    companion object {

        private val TAG = "DocSnippets"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_home, container, false
        )

        (activity as MainActivity).setNavVisible()


//        binding.textView4.text = "123"
//        initializeFirebase()
        initRecycleView()
        mAuth = FirebaseAuth.getInstance()


        return binding.root
    }


    /******************RecycleView 的第一个方法 start*************/
    /*
    *  Recycle View 的第一种方法， 需要第二个 external class 叫 adapter
    *
    * */
//    fun initRecycleView() {
//        val db = FirebaseFirestore.getInstance()
//
//        // Query
//        val query = db.collection(("Canteen"))
//
//        query
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                val adapter = querySnapshot.toObjects(Canteen::class.java)
//                val adapter2 = HomeAdapter()
//                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//                binding.recyclerView.adapter = adapter2
//                adapter2.data = adapter
//            }
//    }
    /******************RecycleView 的第一个方法 end*************/


    /******************RecycleView 的第二个方法 start*************/
///*
//    RecycleView 的第二个方法 --> 直接接 firebase 的 这个是 attach listener 的，
//    就是说有什么东i都是直接 update 的， 会 浪费流量
//*/
//
    fun initRecycleView() {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection(("Canteen")).orderBy("canteen_name", Query.Direction.ASCENDING)



        val options =
            FirestoreRecyclerOptions.Builder<Canteen>()
                .setQuery(query, Canteen::class.java).build()


        adapter = CanteenFireStoreRecyclerAdapter(options, this, context!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

    }

    override fun onItemClick(canteen: Canteen, position: Int) {

        this.findNavController()
            .navigate(HomeFragmentDirections.actionFragmentHomeToCanteenStoreFragment())

    }

    //    private inner class CanteenViewHolder internal constructor(private val view: View) :
//        RecyclerView.ViewHolder(view) {
//        internal fun setCanteenState(canteen: Canteen) {
//            val canteenName = view.findViewById<TextView>(R.id.txtCanteen)
//            canteenName.text = canteen.canteenName
//            val canteenDesciption = view.findViewById<TextView>(R.id.txtDescription)
//            canteenDesciption.text = canteen.type.toString()
//        }
//    }
//
//    private inner class ProductFirestoreRecyclerAdapter internal constructor
//        (options: FirestoreRecyclerOptions<Canteen>) :
//        FirestoreRecyclerAdapter<Canteen, CanteenViewHolder>(options) {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenViewHolder {
//            val view =
//                LayoutInflater.from(parent.context).inflate(R.layout.canteen_row, parent, false)
//            return CanteenViewHolder(view)
//        }
//
//
//        override fun onBindViewHolder(holder: CanteenViewHolder, position: Int, model: Canteen) {
//            holder.setCanteenState(model)
//        }
//    }
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

    /******************RecycleView 的第二个方法 finish*************/
}

/******************Firebase 拿 data 方法 finish*************/
//    private fun initializeFirebase () {
////        val docRef = FirebaseFirestore.getInstance().collection("Canteen")
////            .whereEqualTo("type",1)
////            .get()
////
/****************** 拿一次 *************/
////        docRef.addOnSuccessListener {
////            for (document in it) {
////                Log.d(TAG,"${document.id} => ${document.data}")
////                binding.textView4.append(document["type"].toString())
////            }
////        }  .addOnFailureListener { exception ->
////            Log.w(TAG, "Error getting documents: ", exception)
////        }
//
//
/****************** set up listener to have realtime Update *************/
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
/******************Firebase 拿 data 方法 end*************/

//}

