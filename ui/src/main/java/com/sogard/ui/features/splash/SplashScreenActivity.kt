package com.sogard.ui.features.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.sogard.ui.R
import com.sogard.ui.databinding.ActivitySplashBinding
import com.sogard.ui.generics.navigation.NavigationAwareActivity
import com.sogard.ui.generics.navigation.NavigationResolver


class SplashScreenActivity : NavigationAwareActivity<SplashViewModel>() {

    override fun getNavigationResolver() = NavigationResolver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, SavedStateViewModelFactory(this.application, this))
            .get(SplashViewModel::class.java)

        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel

        viewModel?.initializeAppData()
    }
}