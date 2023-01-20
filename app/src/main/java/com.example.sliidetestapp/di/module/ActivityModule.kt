package com.example.sliidetestapp.di.module

import android.app.Activity
import android.content.Context
import com.example.sliidetestapp.di.qualifier.ActivityContext
import com.example.sliidetestapp.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

  @Provides
  @ActivityScope
  @ActivityContext
  fun provideContext(): Context = activity

}
