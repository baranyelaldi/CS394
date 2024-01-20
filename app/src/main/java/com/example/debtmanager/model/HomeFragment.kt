package com.example.debtmanager.model

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.debtmanager.R
import com.example.debtmanager.databinding.FragmentHomeBinding
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sloganURL = "https://i.ibb.co/7GtNmgR/text.png"
        binding.logoURL = "https://i.ibb.co/thywcHf/logo.png"

        binding.logo.animate().translationY(374f).setDuration(2000).startDelay = 0
        binding.slogan.animate().translationY(-374f).setDuration(2000).startDelay = 0

        Handler().postDelayed({
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_recyclerViewFragment)
        }, 4000)
    }
}