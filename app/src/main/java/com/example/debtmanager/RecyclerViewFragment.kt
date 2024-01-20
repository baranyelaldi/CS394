package com.example.debtmanager

import com.example.debtmanager.Friend
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debtmanager.data.debts
import com.example.debtmanager.data.images
import com.example.debtmanager.data.names
import com.example.debtmanager.databinding.FragmentRecyclerViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecyclerViewBinding.bind(view)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        var totalDebt = 0

        friendList = ArrayList()


        val totalDebtString = "Total Debt: $totalDebt"
        binding.totalDebtTextView.text = totalDebtString

        friendAdapter = FriendAdapter(object : FriendAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedFriend: Friend = friendList[position]

                val bundle = Bundle()
                bundle.putParcelable("clickedFriend", clickedFriend)
                bundle.putInt("clickedPosition", position)

                val friendFragment = FriendFragment()
                friendFragment.arguments = bundle

                view.findNavController()
                    .navigate(R.id.action_recyclerViewFragment_to_friendFragment, bundle)
            }
        })

        recyclerView.adapter = friendAdapter

        getFriends()

        friendAdapter.submitList(friendList)

        binding.addFriendButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_recyclerViewFragment_to_addFriendFragment)
        }
    }

    private fun getFriends() {

        dbRef = FirebaseDatabase.getInstance().getReference("Friends")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()
                if (snapshot.exists()) {
                    for (friendSnap in snapshot.children) {
                        val debt = friendSnap.child("debt").getValue(Int::class.java) ?: 0
                        val image = friendSnap.child("image").getValue(Int::class.java) ?: 0
                        val name = friendSnap.child("name").getValue(String::class.java) ?: ""

                        val friendData = Friend(image, name, debt)
                        friendList.add(friendData)
                    }
                    friendAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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