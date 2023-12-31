package com.example.debtmanager

import Friend
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debtmanager.databinding.FragmentRecyclerViewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecyclerViewBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendList: ArrayList<Friend>
    private lateinit var friendAdapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecyclerViewBinding.bind(view)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        friendList = ArrayList()

        friendList.add(Friend(R.drawable.male, "Baran Yelaldi", 300))
        friendList.add(Friend(R.drawable.male, "Emirhan Alabas", -100))
        friendList.add(Friend(R.drawable.male, "Egemen Yorulmaz", 0))
        friendList.add(Friend(R.drawable.male, "Yarkin Tarcin", 0))
        friendList.add(Friend(R.drawable.male, "Oruc Yigit Solak", 0))
        friendList.add(Friend(R.drawable.male, "Bercan Yelaldi", 0))

        friendAdapter = FriendAdapter(friendList)
        recyclerView.adapter = friendAdapter
        friendAdapter.setOnItemClickListener(object : FriendAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedFriend: Friend = friendList[position]

                // Pass data to the FriendFragment using Bundle
                val bundle = Bundle()
                bundle.putParcelable("clickedFriend", clickedFriend)

                val friendFragment = FriendFragment()
                friendFragment.arguments = bundle

                view.findNavController().navigate(R.id.action_recyclerViewFragment_to_friendFragment, bundle)
            }
        })

        binding.addFriendButton.setOnClickListener{
            view.findNavController().navigate(R.id.action_recyclerViewFragment_to_addFriendFragment)
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecyclerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}