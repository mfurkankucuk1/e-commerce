package com.mfk.roomexample.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentLoginBinding
import com.mfk.roomexample.utils.Constants
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.utils.ErrorPopupHelper
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
        setupEdittext()
    }

    private fun subscribeObserve() {
        userViewModel.userLoginResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                if (result) {
                    userViewModel.clearUserLoginResponse()
                    userViewModel.getUserForLogin(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                } else {
                    userViewModel.clearUserLoginResponse()
                    ErrorPopupHelper.showErrorDialog(
                        requireContext(), layoutInflater,
                        resources.getString(R.string.please_check_your_information)
                    )
                }
            }
        }

        userViewModel.getUserForLoginResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                setupNavigateProducts()
                setupSetPreferences(result.id)
            }
        }

    }

    private fun setupSetPreferences(id: String) {
        preferencesRepository.setStringPreferences(USER_UUID, id)
        preferencesRepository.setBooleanPreferences(Constants.USER_LOGGED_IN, true)
    }

    private fun setupNavigateProducts() {
        findNavController().navigate(R.id.loginFragment_to_productFragment)
    }

    private fun handleClickEvents() {
        binding.apply {
            btnLogin.setOnClickListener {
                checkRegister("")
                if (checkError()) {
                    userViewModel.loginUser(etEmail.text.toString(), etPassword.text.toString())
                }
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
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
    }

    private fun checkError(): Boolean {
        binding.apply {
            return layoutEmail.error == null && layoutPassword.error == null
        }
    }

    private fun setupEdittext() {
        binding.apply {
            addTextChangedListener(etEmail, layoutEmail, getString(R.string.type_email))
            addTextChangedListener(etPassword, layoutPassword, getString(R.string.type_password))
        }
    }

    private fun addTextChangedListener(
        edittext: TextInputEditText,
        textInputLayout: TextInputLayout,
        type: String
    ) {
        edittext.doOnTextChanged { inputText, _, _, _ ->
            if (inputText != null && inputText.isNotEmpty()) {
                val currentTextLength = inputText.length
                Handler(Looper.getMainLooper()).postDelayed({
                    if (currentTextLength == inputText.length) {
                        if (_binding != null) {
                            checkRegister(type)
                        }
                    }
                }, 2000)
            } else {
                setupInputError(textInputLayout, edittext, null)
            }
        }


    }

    private fun checkRegister(type: String) {
        binding.apply {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            when (type) {
                getString(R.string.type_email) -> {
                    when {
                        email.isEmpty() -> {
                            setupInputError(
                                layoutEmail,
                                etEmail,
                                getString(R.string.email_cannot_empty)
                            )
                        }
                        else -> {
                            setupInputError(layoutEmail, etEmail, null)
                        }
                    }
                }
                getString(R.string.type_password) -> {
                    when {
                        password.isEmpty() -> {
                            setupInputError(
                                layoutPassword,
                                etPassword,
                                getString(R.string.password_cannot_empty)
                            )
                        }
                        password.length < 6 -> {
                            setupInputError(
                                layoutPassword,
                                etPassword,
                                getString(R.string.password_min_six_character)
                            )
                        }
                        else -> {
                            setupInputError(layoutPassword, etPassword, null)
                        }
                    }
                }
                else -> {
                    checkAll()
                }
            }
        }
    }

    private fun checkAll() {
        checkRegister(getString(R.string.type_email))
        checkRegister(getString(R.string.type_password))
    }

    private fun setupInputError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        error: String?
    ) {
        if (error != null) {
            textInputLayout.error = error
            textInputEditText.setBackgroundResource(R.drawable.background_input_error)
        } else {
            textInputLayout.error = null
            textInputEditText.setBackgroundResource(R.drawable.background_input)
        }
    }

}