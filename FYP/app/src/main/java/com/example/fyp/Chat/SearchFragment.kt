package com.example.fyp.Chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Chat.Item.PersonItem
import com.example.fyp.Util.FirestoreUtil
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentSearchBinding
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import org.jetbrains.anko.support.v4.intentFor


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var userListenerRegistration: ListenerRegistration
    private var shouldIdInitRecyclerView = true
    private lateinit var peopleSection: Section

    private lateinit var userViewModel: UserViewModel

//    private var userAdapter: UserAdapter? = null
//    private var mUsers: List<User>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_search, container, false
        )

        userListenerRegistration = FirestoreUtil.addUserListener(this.activity!!, this::updateRecyclerView)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListenerRegistration)
        shouldIdInitRecyclerView = true
    }

    private fun updateRecyclerView(items: List<Item>) {
        fun init(){
            binding.rvUser.apply {
                layoutManager = LinearLayoutManager(this@SearchFragment.context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldIdInitRecyclerView = false

        }
        fun updateItems() = peopleSection.update(items)

        if(shouldIdInitRecyclerView){
            init()
        }
        else {
            updateItems()
        }
    }

    private val onItemClick = OnItemClickListener { item, view ->
        if (item is PersonItem) {

                startActivity(intentFor<ChatActivity>(
                    AppConstant.USER_NAME to item.person.phone_number,
                    AppConstant.USER_ID to item.userId)
                )

        }

    }

}
