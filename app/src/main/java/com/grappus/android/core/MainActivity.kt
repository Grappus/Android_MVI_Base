package com.grappus.android.core

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.grappus.android.basemvi.R
import com.grappus.android.basemvi.databinding.ActivityMainBinding
import com.grappus.android.di.ActivityComponent
import com.grappus.android.di.AppComponent
import com.grappus.android.di.DaggerActivityComponent
import com.grappus.android.listeners.ToolbarListener
import com.grappus.android.providers.BindActivity
import com.grappus.android.core.contracts.MainContract

class MainActivity :
    AbstractActivity<MainContract.ViewState, MainContract.ViewEvent, MainContract.ViewModel>(),
    ToolbarListener {

    private val binding: ActivityMainBinding by BindActivity(R.layout.activity_main)

    private lateinit var navController: NavController

    private val component: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .appComponent(appComponent as AppComponent)
            .activity(this)
            .build()
    }

    private val appComponent: AppComponent?
        get() = (application as BaseApplication).component

    override fun injector() = component

    override fun getViewModelClass() = MainContract.ViewModel::class

    override fun setUpActivity(savedInstanceState: Bundle?) {
        injector().inject(this)
        binding.executePendingBindings()
        navController = findNavController(R.id.main_container)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment is ToolbarListener) {
            currentFragment.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Custom methods
     */
    private fun getCurrentFragment(): Fragment {
        val manager =
            (supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).childFragmentManager
        return manager.fragments[0]
    }

    /**
     * In order to pass results to child fragments, parent activity should pass the result by calling super
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}