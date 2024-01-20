package com.example.debtmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.navigation.findNavController
import com.example.debtmanager.data.debts
import com.example.debtmanager.data.images
import com.example.debtmanager.data.names
import com.example.debtmanager.databinding.FragmentAddFriendBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class addFriendFragment : Fragment(R.layout.fragment_add_friend) {
    private lateinit var binding: FragmentAddFriendBinding
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddFriendBinding.bind(view)

        binding.buttonLogin.setOnClickListener{
            if(validate()){
                debts.add(binding.addDebt.text.toString().toInt())
                names.add(binding.addName.text.toString())
                if(binding.radioGroup.checkedRadioButtonId == 0){
                    images.add(R.drawable.female)
                }else{
                    images.add(R.drawable.male)
                }
                view.findNavController().navigate(R.id.action_addFriendFragment_to_recyclerViewFragment)
            }else{
                Toast.makeText(requireContext(), "Please enter all fields!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun validate() : Boolean{
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addFriendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addFriendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}