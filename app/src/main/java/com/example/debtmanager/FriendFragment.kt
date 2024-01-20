package com.example.debtmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.debtmanager.data.debts
import com.example.debtmanager.data.images
import com.example.debtmanager.data.names
import com.example.debtmanager.databinding.FragmentFriendBinding
import com.example.debtmanager.viewmodel.DebtViewModel
import com.example.debtmanager.Friend
import com.example.debtmanager.databinding.FragmentRecyclerViewBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FriendFragment : Fragment(R.layout.fragment_friend) {

    private lateinit var binding: FragmentFriendBinding

    private val viewModel: DebtViewModel by viewModels()
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friend, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val bundle = arguments
        if (bundle != null && bundle.containsKey("clickedFriend")) {
            val clickedFriend: Friend? = bundle.getParcelable("clickedFriend")
            val clickedPosition: Int? = bundle.getInt("clickedPosition")
            clickedFriend?.let {
                binding.name.text = it.name
                binding.titleImage.setImageResource(it.image)
                viewModel.setDebt(clickedFriend.debt)

                val friendId = it.id

                binding.borrow.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    binding.viewmodel!!.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(it, true)
                    debts[clickedPosition!!] = viewModel.debt.value!!

                    updateDatabase(friendId, viewModel.debt.value)

                    binding.change.text.clear()
                }

                binding.pay.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    binding.viewmodel!!.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(it, false)
                    debts[clickedPosition!!] = viewModel.debt.value!!

                    updateDatabase(friendId, viewModel.debt.value)

                    binding.change.text.clear()
                }



                binding.removeFriend.setOnClickListener{
                    if(viewModel.debt.value == 0){
                        debts.removeAt(clickedPosition!!)
                        names.removeAt(clickedPosition!!)
                        images.removeAt(clickedPosition!!)

                        deleteFromDatabase(friendId)

                        view.findNavController().navigate(R.id.action_friendFragment_to_recyclerViewFragment)
                        Toast.makeText(requireContext(), "Friend is removed.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Settle the debt before removing the friend!", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }


    }

    private fun updateDatabase(friendId: String, debt: Int?) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Friends").child(friendId)
        dbRef.child("debt").setValue(debt)
    }

    private fun deleteFromDatabase(friendId: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Friends").child(friendId)
        dbRef.removeValue()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FriendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FriendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}