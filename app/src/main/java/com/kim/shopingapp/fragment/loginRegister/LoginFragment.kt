package com.kim.shopingapp.fragment.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kim.shopingapp.R
import com.kim.shopingapp.activities.ShoppingActivity
import com.kim.shopingapp.databinding.FragmentLoginBinding
import com.kim.shopingapp.dialog.setUpBottomSheetDialog
import com.kim.shopingapp.util.Resource
import com.kim.shopingapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    //get the viewModel
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ifYouDont.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            buttonLoginLogin.setOnClickListener {
                //trim email for spaces not the password
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                //call the login function from the viewModel
                viewModel.login(email, password)
            }
        }

        //forgetPassword using resetPasswordDialog.kt
        binding.tvForgetPasswordLogin.setOnClickListener {
            setUpBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }
        //listen to resetPassword and collect that flow
        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(), "Reset link was sent to your email", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "Cold not send reset password link ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        //coroutine scope outside binding to listen to the sharedFlow
        //launchWhenStarted is used with flows to make them aware of the lifecycle so when the app is stopped in the
        //background launchWhenStarted will make them unsubscribe from that flow
        lifecycleScope.launchWhenStarted {
            //collect the login flow and use when to check each resource
            viewModel.login.collect {
                when (it) {
                    is Resource.Loading -> {
                        //animate the loading button
                        binding.buttonLoginLogin.startAnimation()
                    }
                    is Resource.Success -> {
                        //push to shopping activity and clear the activity from task
                        //revert the animation cause this could lead to a memory leak
                        binding.buttonLoginLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        //show toast to user and revert the animation
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.buttonLoginLogin.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
    }
}