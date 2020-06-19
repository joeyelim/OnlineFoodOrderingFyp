package com.example.fyp.OrderingModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fyp.MainActivity

import com.example.fyp.databinding.FragmentCurrentOrderBinding

import android.os.Parcelable
import android.util.Log


class CurrentOrderFragment : Fragment() {

    private lateinit var binding: FragmentCurrentOrderBinding
    private val ARG_PLAYERS = "arg_player"
    private var player : String = "13"

    /* --------this is for viewpage------------------*/
//    fun newInstance(players: String): CurrentOrderFragment {
//
//        val args = Bundle()
//        args.putString(ARG_PLAYERS, players)
//        val fragment = CurrentOrderFragment()
//        fragment.arguments = args
//        return fragment
//    }
//
//    override fun onCreate( savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val args = arguments
//        if (args != null) {
//            this.player = args.getString(ARG_PLAYERS, "123")
//        }
//        requireNotNull(player) { "Player list can not be null" }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_current_order, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

//        var b: TextView = view.findViewById(com.example.fyp.R.id.textView5)
//        b.text = abba1


        return binding.root
    }


    /* --------this is for viewpage------------------*/

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        (view.findViewById(com.example.fyp.R.id.textView5) as TextView).text = player
//    }



}