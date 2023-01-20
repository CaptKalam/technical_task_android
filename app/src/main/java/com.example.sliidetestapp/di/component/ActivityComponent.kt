package com.example.sliidetestapp.di.component

import com.example.sliidetestapp.di.module.ActivityBindingModule
import com.example.sliidetestapp.di.module.ActivityModule
import com.example.sliidetestapp.di.scope.ActivityScope
import com.example.sliidetestapp.MainActivity
import dagger.Component

@ActivityScope
@Component(
  dependencies = [ApplicationComponent::class],
  modules = [ActivityModule::class, ActivityBindingModule::class]
)
interface ActivityComponent {

  fun inject(activity: MainActivity)

}
