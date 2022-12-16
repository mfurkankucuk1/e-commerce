package com.mfk.roomexample.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentSettingsBinding
import com.mfk.roomexample.utils.Constants.USER_LOGGED_IN
import com.mfk.roomexample.utils.Constants.USER_UUID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.apply {
            btnLogout.setOnClickListener {
                preferencesRepository.apply {
                    deletePreferences(USER_UUID)
                    setBooleanPreferences(USER_LOGGED_IN, false)
                }
            }
            imgBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initialize() {

    }

}