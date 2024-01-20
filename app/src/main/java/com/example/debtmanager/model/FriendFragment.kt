package com.example.debtmanager.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.debtmanager.R
import com.example.debtmanager.databinding.FragmentFriendBinding
import com.example.debtmanager.viewmodel.DebtViewModel
import com.google.firebase.database.FirebaseDatabase

class FriendFragment : Fragment(R.layout.fragment_friend) {

    private lateinit var binding: FragmentFriendBinding

    private val viewModel: DebtViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            clickedFriend?.let {
                binding.name.text = it.name
                binding.titleImage.setImageResource(it.image)
                viewModel.setDebt(clickedFriend.debt)

                val friendId = it.id

                binding.borrow.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    binding.viewmodel!!.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(true)

                    updateDatabase(friendId, viewModel.debt.value)

                    binding.change.text.clear()
                }

                binding.pay.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    binding.viewmodel!!.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(false)

                    updateDatabase(friendId, viewModel.debt.value)

                    binding.change.text.clear()
                }



                binding.removeFriend.setOnClickListener{
                    if(viewModel.debt.value == 0){
                        deleteFromDatabase(friendId)

                        view.findNavController().navigate(R.id.action_friendFragment_to_recyclerViewFragment)
                        Toast.makeText(requireContext(), "Friend is removed.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Settle the debt before removing the friend!",
                            Toast.LENGTH_SHORT
                        ).show()
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
}