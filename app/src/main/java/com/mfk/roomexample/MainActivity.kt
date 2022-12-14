package com.mfk.roomexample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mfk.mylibrary.remove
import com.mfk.mylibrary.show
import com.mfk.roomexample.databinding.ActivityMainBinding
import com.mfk.roomexample.viewModel.ProductViewModel
import com.mfk.roomexample.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private lateinit var navController: NavController
    private val productViewModel: ProductViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationController()
        setupFabNavigate()
    }

    private fun setupFabNavigate() {
        binding.apply {
            btnMainScreen.setOnClickListener {
                navController.navigate(R.id.discoverFragment)
            }
        }
    }

    private fun setupNavigationController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(1).isEnabled = false
    }

    fun showBottomNavigation(){
        binding.bottomNavigation.show()
        binding.btnMainScreen.show()
        binding.line.show()
        binding.bottomBar.show()
    }
    fun hideBottomNavigation(){
        binding.bottomNavigation.remove()
        binding.btnMainScreen.remove()
        binding.line.remove()
        binding.bottomBar.remove()
    }
}