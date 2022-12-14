package com.mfk.roomexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentSplashBinding
import com.mfk.roomexample.utils.Constants.USER_LOGGED_IN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let {
            it.hideBottomNavigation()
        }
        setupUserControl()
    }

    private fun setupUserControl() {
        if (!preferencesRepository.getBooleanPreferences(
                USER_LOGGED_IN
            )
        ) {
            findNavController().navigate(R.id.splashFragment_to_loginFragment)
        }else{
            findNavController().navigate(R.id.splashFragment_to_productFragment)
        }
    }
}