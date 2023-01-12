package com.mfk.roomexample.ui.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.model.User
import com.mfk.roomexample.databinding.FragmentAccountSettingBinding
import com.mfk.roomexample.utils.CurrentTimeHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AccountSettingFragment : Fragment() {

    private var _binding: FragmentAccountSettingBinding? = null
    private val binding: FragmentAccountSettingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupEdittext()
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.apply {
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnUpdate.setOnClickListener {
                checkUpdateAll()
                if (checkError()){
                    val user = User(
                        id = UUID.randomUUID().toString(),
                        userName = etUsername.text.toString(),
                        name = etName.text.toString(),
                        surname = etSurname.text.toString(),
                        email = etEmail.text.toString(),
                        password = etPassword.text.toString(),
                    )

                }
            }
        }
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
    }

    private fun setupEdittext() {
        binding.apply {
            addTextChangedListener(etName, layoutName, getString(R.string.type_name))
            addTextChangedListener(etSurname, layoutSurname, getString(R.string.type_surname))
            addTextChangedListener(etEmail, layoutEmail, getString(R.string.type_email))
            addTextChangedListener(etPassword, layoutPassword, getString(R.string.type_password))
            addTextChangedListener(etUsername, layoutPassword, getString(R.string.type_username))
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
                        if (_binding != null) checkRegister(type)
                    }
                }, 2000)
            } else {
                setupInputError(textInputLayout, edittext, null)
            }
        }
    }

    private fun checkUpdateAll() {
        checkRegister(getString(R.string.type_name))
        checkRegister(getString(R.string.type_surname))
        checkRegister(getString(R.string.type_email))
        checkRegister(getString(R.string.type_password))
        checkRegister(getString(R.string.type_username))
    }

    private fun checkRegister(type: String) {
        binding.apply {
            val email = etEmail.text.toString().trim()
            val name = etName.text.toString().trim()
            val surname = etSurname.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val username = etUsername.text.toString().trim()
            when (type) {
                getString(R.string.type_name) -> {
                    when {
                        name.isEmpty() -> {
                            setupInputError(
                                layoutName,
                                etName,
                                getString(R.string.name_cannot_empty)
                            )
                        }
                        name.length < 2 -> {
                            setupInputError(
                                layoutName,
                                etName,
                                getString(R.string.name_min_two_character)
                            )
                        }
                        else -> {
                            setupInputError(layoutName, etName, null)
                        }
                    }
                }
                getString(R.string.type_surname) -> {
                    when {
                        surname.isEmpty() -> {
                            setupInputError(
                                layoutSurname,
                                etSurname,
                                getString(R.string.surname_cannot_empty)
                            )
                        }
                        surname.length < 2 -> {
                            setupInputError(
                                layoutSurname,
                                etSurname,
                                getString(R.string.surname_min_two_character)
                            )
                        }
                        else -> {
                            setupInputError(layoutSurname, etSurname, null)
                        }
                    }
                }
                getString(R.string.type_email) -> {
                    when {
                        email.isEmpty() -> {
                            setupInputError(
                                layoutEmail,
                                etEmail,
                                getString(R.string.email_cannot_empty)
                            )
                        }
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            setupInputError(
                                layoutEmail,
                                etEmail,
                                getString(R.string.enter_correct_email)
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
                getString(R.string.type_username) -> {
                    when {
                        username.isEmpty() -> {
                            setupInputError(
                                layoutUsername,
                                etUsername,
                                getString(R.string.username_cannot_empty)
                            )
                        }
                        surname.length < 2 -> {
                            setupInputError(
                                layoutUsername,
                                etUsername,
                                getString(R.string.username_min_two_character)
                            )
                        }
                        else -> {
                            setupInputError(layoutUsername, etUsername, null)
                        }
                    }
                }
            }
        }
    }

    private fun setupInputError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        error: String?
    ) {
        if (error != null) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = error
            textInputEditText.setBackgroundResource(R.drawable.background_input_error)
        } else {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
            textInputEditText.setBackgroundResource(R.drawable.background_input)
        }
    }

    private fun checkError(): Boolean {
        binding.apply {
            return layoutName.error == null
                    && layoutSurname.error == null
                    && layoutEmail.error == null
                    && layoutPassword.error == null
                    && layoutUsername.error == null
        }
    }


}