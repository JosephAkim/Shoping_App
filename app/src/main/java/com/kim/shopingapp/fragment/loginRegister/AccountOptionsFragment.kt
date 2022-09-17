package com.kim.shopingapp.fragment.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kim.shopingapp.R
import com.kim.shopingapp.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment: Fragment(R.layout.fragment_account_options) {
    private lateinit var binding:FragmentAccountOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLoginAccountOptions.setOnClickListener {
            //navigate to logIn fragment
            findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }
        binding.buttonRegisterAccountOptions.setOnClickListener {
            //navigate to register fragment
            findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }
    }
}