package com.example.sliidetestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sliidetestapp.di.scope.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
  ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return getViewModel(modelClass)
  }

  private fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
    val viewModels = viewModelProviders.filterKeys { modelClass.isAssignableFrom(it) }
    @Suppress("UNCHECKED_CAST")
    return when (viewModels.size) {
      1 -> viewModels.values.first().get() as T
      0 -> throw IllegalArgumentException("There is no ViewModel for this class type")
      else -> throw IllegalArgumentException("There is more than one ViewModel for this class type")
    }
  }
}
