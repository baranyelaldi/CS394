package com.example.debtmanager

import DebtViewModel
import Friend
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.debtmanager.data.debts
import com.example.debtmanager.data.images
import com.example.debtmanager.data.names
import com.example.debtmanager.databinding.FragmentFriendBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FriendFragment : Fragment(R.layout.fragment_friend) {
    private lateinit var binding: FragmentFriendBinding
    private val viewModel: DebtViewModel by viewModels()
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
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendBinding.bind(view)



        val bundle = arguments
        if (bundle != null && bundle.containsKey("clickedFriend")) {
            val clickedFriend: Friend? = bundle.getParcelable("clickedFriend")
            val clickedPosition: Int? = bundle.getInt("clickedPosition")
            clickedFriend?.let {
                binding.name.text = it.name
                binding.titleImage.setImageResource(it.image)
                viewModel.setDebt(clickedFriend.debt)

                binding.borrow.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    viewModel.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(it, true)
                }

                binding.pay.setOnClickListener { _ ->
                    val newValueFromTextField = binding.change.text.toString().toIntOrNull() ?: 0
                    viewModel.setChangeDebt(newValueFromTextField)
                    viewModel.changeFriendDebt(it, false)
                }

                viewModel.changeDebt.observe(viewLifecycleOwner) {
                        newValue -> binding.change.setText(newValue.toString())
                        debts[clickedPosition!!] = viewModel.debt.value!!
                }

                viewModel.debt.observe(viewLifecycleOwner) {
                        newValue -> binding.debt.setText(newValue.toString())
                        debts[clickedPosition!!] = viewModel.debt.value!!
                }

                binding.removeFriend.setOnClickListener{
                    if(viewModel.debt.value == 0){
                        debts.removeAt(clickedPosition!!)
                        names.removeAt(clickedPosition!!)
                        images.removeAt(clickedPosition!!)

                        view.findNavController().navigate(R.id.action_friendFragment_to_recyclerViewFragment)
                    }else{
                        Toast.makeText(requireContext(), "Settle the debt before removing the friend!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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