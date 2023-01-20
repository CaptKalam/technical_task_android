package com.example.sliidetestapp.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.sliidetestapp.SliideTestApp
import com.example.sliidetestapp.di.module.ApplicationBindingModule
import com.example.sliidetestapp.di.module.ApplicationModule
import com.example.sliidetestapp.di.module.ViewModelModule
import com.example.sliidetestapp.di.qualifier.ApplicationContext
import com.example.sliidetestapp.di.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(
  modules = [ApplicationModule::class, ViewModelModule::class, ApplicationBindingModule::class]
)
interface ApplicationComponent {

  fun inject(application: SliideTestApp)

  @ApplicationContext
  fun applicationContext(): Context

  fun viewModelFactory(): ViewModelProvider.Factory
}
