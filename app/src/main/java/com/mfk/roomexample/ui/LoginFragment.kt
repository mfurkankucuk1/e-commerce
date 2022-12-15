package com.mfk.roomexample.ui

import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mfk.roomexample.R
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentLoginBinding
import com.mfk.roomexample.utils.Constants
import com.mfk.roomexample.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
        subscribeObserve()
        setupRegister()
    }

    private fun subscribeObserve() {
        userViewModel.userLoginResponse.observe(viewLifecycleOwner){response->
            response?.let { result->
                if (result){
                    userViewModel.clearUserLoginResponse()
                    findNavController().navigate(R.id.loginFragment_to_productFragment)
                    preferencesRepository.setBooleanPreferences(Constants.USER_LOGGED_IN,true)
                }else{
                    userViewModel.clearUserLoginResponse()
                    Toast.makeText(requireContext(), "Check your info", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleClickEvents() {
        binding.apply {
            btnLogin.setOnClickListener {
              userViewModel.loginUser(etEmail.text.toString(),etPassword.text.toString())
            }
        }
    }

    private fun setupRegister() {
        binding.apply {
            val spannableString = SpannableString(getString(R.string.dont_have_an_account_register))
            val clickableSpanRegister = object : ClickableSpan() {
                override fun onClick(p0: View) {
                    setupNavigateRegister()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ds.color = requireContext().getColor(R.color.teal_700)
                    }
                    ds.isUnderlineText = false

                }
            }
            spannableString.setSpan(clickableSpanRegister, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvRegister.text = spannableString
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    private fun setupNavigateRegister() {
        findNavController().navigate(R.id.loginFragment_to_registerFragment)
    }

    private fun initialize() {

    }

}