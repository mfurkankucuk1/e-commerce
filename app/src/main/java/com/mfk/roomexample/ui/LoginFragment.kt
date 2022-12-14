package com.mfk.roomexample.ui

import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mfk.roomexample.R
import com.mfk.roomexample.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

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
        setupRegister()
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