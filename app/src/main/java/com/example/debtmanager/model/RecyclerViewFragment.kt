package com.example.debtmanager.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debtmanager.R
import com.example.debtmanager.databinding.FragmentRecyclerViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {
    private lateinit var binding: FragmentRecyclerViewBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendList: ArrayList<Friend>
    private lateinit var friendAdapter: FriendAdapter

    private lateinit var dbRef: DatabaseReference

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

        friendList = ArrayList()

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
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()
                if (snapshot.exists()) {
                    for (friendSnap in snapshot.children) {
                        val friendId = friendSnap.key ?: ""
                        val debt = friendSnap.child("debt").getValue(Int::class.java) ?: 0
                        val image = friendSnap.child("image").getValue(Int::class.java) ?: 0
                        val name = friendSnap.child("name").getValue(String::class.java) ?: ""

                        val friendData = Friend(image, name, debt, friendId)
                        friendList.add(friendData)
                    }
                    friendAdapter.notifyDataSetChanged()

                    var totalDebt = 0

                    for(friend in friendList) {
                        totalDebt += friend.debt
                    }

                    val totalDebtString = "Total Debt: $totalDebt₺"
                    binding.totalDebtTextView.text = totalDebtString
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}