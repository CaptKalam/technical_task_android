package com.example.sliidetestapp.di.key

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.BINARY)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
