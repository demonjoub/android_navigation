package cuzhy.com.navigation

import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    var currentScreen: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.fragment)

        //Setting the navigation controller to Bottom Nav
        bottomNav.setOnNavigationItemSelectedListener { item ->
            val visibleFragment = getCurrentFragment()
            when (item.itemId) {
                R.id.homeFragment -> {
                    var fwd = true
                    if (currentScreen > 0) {
                        fwd = false
                    }
                    if (currentScreen != 0) {
                        val homeFragment = HomeFragment()
                        openFragment(homeFragment, fwd)
                    }
                    currentScreen = 0
                }
                R.id.favsFragment -> {
                    var fwd = true
                    if (currentScreen > 1) {
                        fwd = false
                    }
                    if (currentScreen != 1) {
                        val favsFragment = FavsFragment()
                        openFragment(favsFragment, fwd)
                    }
                    currentScreen = 1
                }
                R.id.settingsFragment -> {
                    if (currentScreen != 2) {
                        val settingsFragment = SettingsFragment()
                        openFragment(settingsFragment, true)
                    }
                    currentScreen = 2
                }
            }
            true
        }
//        val visibleFragment = getCurrentFragment()
//        bottomNav.setupWithNavController(navController)


        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    fun openFragment(fragment: Fragment, fwd: Boolean) {
        var fragmentManager = supportFragmentManager
        var transition = fragmentManager.beginTransaction()
        if (fwd) {
            transition.setCustomAnimations(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right
            )
        } else {
            transition.setCustomAnimations(
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right,
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left
            )
        }

        transition.addToBackStack(null)
        transition.add(R.id.fragment, fragment, "BLANK_FRAGMENT").commit()
    }

    fun getCurrentFragment(): Fragment {
        var getcurrentFragment = supportFragmentManager.findFragmentById(R.id.fragment) as Fragment
        return getcurrentFragment
    }

}

