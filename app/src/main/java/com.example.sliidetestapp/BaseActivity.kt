package com.example.sliidetestapp

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sliidetestapp.di.component.ApplicationComponent
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val applicationComponent: ApplicationComponent
        get() = (application as SliideTestApp).component

    protected fun <T : ViewModel> getViewModelInstance(viewModelClass: KClass<T>): T {
        return ViewModelProvider(this, viewModelFactory)[viewModelClass.java]
    }
}