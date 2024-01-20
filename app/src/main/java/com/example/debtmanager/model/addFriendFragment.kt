package com.example.debtmanager.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.debtmanager.R
import com.example.debtmanager.databinding.FragmentAddFriendBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addFriendFragment : Fragment(R.layout.fragment_add_friend) {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var dbRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddFriendBinding.bind(view)
        dbRef = FirebaseDatabase.getInstance().getReference("Friends")

        binding.buttonLogin.setOnClickListener{
            if(validate()){
                saveFriendData()
                view.findNavController().navigate(R.id.action_addFriendFragment_to_recyclerViewFragment)
            }else{
                Toast.makeText(requireContext(), "Please enter all fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate() : Boolean{
        if(binding.addName.text.isEmpty()){
            return false
        }
        if(binding.addDebt.text.isEmpty()){
            return false
        }
        if(binding.radioGroup.checkedRadioButtonId == -1){
            return false
        }
        return true
    }

    private fun saveFriendData() {
        val friendName = binding.addName.text.toString()
        val friendDebt = binding.addDebt.text.toString().toInt()
        val friendId = dbRef.push().key!!
        val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
        val imageId = if (selectedRadioButtonId == R.id.Male) {
            R.drawable.male
        } else {
            R.drawable.female
        }

        val friend = Friend(
            imageId,
            friendName,
            friendDebt,
            friendId
        )
        dbRef.child(friendId).setValue(friend)
            .addOnCompleteListener{
                Toast.makeText(requireContext(), "Friend added successfully", Toast.LENGTH_LONG).show()
            } .addOnFailureListener{
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
    }
}