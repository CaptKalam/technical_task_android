package com.example.sliidetestapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sliidetestapp.di.key.ViewModelKey
import com.example.sliidetestapp.viewmodel.MainActivityViewModel
import com.example.sliidetestapp.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainActivityViewModel::class)
  abstract fun provideMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

}
