package com.mfk.roomexample.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mfk.roomexample.MainActivity
import com.mfk.roomexample.databinding.FragmentAccountSettingBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun initialize() {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.hideBottomNavigation()
        }
    }
}