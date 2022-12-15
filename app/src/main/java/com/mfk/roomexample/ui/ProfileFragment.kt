package com.mfk.roomexample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mfk.roomexample.data.repository.PreferencesRepository
import com.mfk.roomexample.databinding.FragmentProfileBinding
import com.mfk.roomexample.utils.Constants.USER_UUID
import com.mfk.roomexample.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeObserve()
    }

    private fun subscribeObserve() {
        userViewModel.getUserResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                Log.e("TAG", "subscribeObserve: ${result.id}")
            }
        }
    }

    private fun initialize() {
        userViewModel.getUser(preferencesRepository.getStringPreferences(USER_UUID))
    }
}