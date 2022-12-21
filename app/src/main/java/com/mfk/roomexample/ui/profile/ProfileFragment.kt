package com.mfk.roomexample.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.R
import com.mfk.roomexample.data.model.User
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
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.apply {
            imgSettings.setOnClickListener {
                findNavController().navigate(R.id.profileFragment_to_settingsFragment)
            }
            mcFavorite.setOnClickListener {
                findNavController().navigate(R.id.profileFragment_to_favoriteFragment)
            }
            mcBasket.setOnClickListener {
                findNavController().navigate(R.id.profileFragment_to_cartFragment)
            }
        }
    }

    private fun subscribeObserve() {
        userViewModel.getUserResponse.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                handleUserResponse(result)
                userViewModel.clearUserResponse()
            }
        }
    }

    private fun handleUserResponse(result: User) {
        binding.apply {
            tvProfileImage.text = "${result.name?.substring(0, 1)?.uppercase()}.${
                result.surname?.substring(0, 1)?.uppercase()
            }"

            tvNameSurname.text = "${result.name} ${result.surname}"
        }
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.showBottomNavigation()
        }
        userViewModel.getUser(preferencesRepository.getStringPreferences(USER_UUID))
    }
}
