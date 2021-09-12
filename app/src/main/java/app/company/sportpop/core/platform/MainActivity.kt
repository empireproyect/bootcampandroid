package app.company.sportpop.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.company.sportpop.R
import dagger.hilt.android.AndroidEntryPoint
import app.company.sportpop.core.connectivity.base.ConnectivityProvider
import app.company.sportpop.core.extensions.alert
import app.company.sportpop.core.extensions.hide
import app.company.sportpop.core.extensions.show
import app.company.sportpop.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ConnectivityProvider.ConnectivityStateListener {

    @Inject
    lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.addFragment, R.id.profileFragment, R.id.loginFragment, R.id.registerFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        changeNavigation()
    }

    private fun changeNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment, R.id.registerFragment -> binding.bottomNavigationView.hide()
                else -> binding.bottomNavigationView.show()
            }
        }
    }

    fun showToolbar() = supportActionBar?.show()
    fun hideToolbar() = supportActionBar?.hide()

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if (!state.isNetworkAvailable()) {
            this.alert(getString(R.string.error), getString(R.string.not_internet_available), R.color.yellow)
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityProvider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityProvider.removeListener(this)
    }
}
