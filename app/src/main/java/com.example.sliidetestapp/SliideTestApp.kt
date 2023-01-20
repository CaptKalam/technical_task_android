package com.example.sliidetestapp

import android.app.Application
import com.example.sliidetestapp.di.component.ApplicationComponent
import com.example.sliidetestapp.di.component.DaggerApplicationComponent
import com.example.sliidetestapp.di.module.ApplicationModule

open class SliideTestApp : Application() {

    open lateinit var component: ApplicationComponent
        protected set

    override fun onCreate() {
        initDagger()
        super.onCreate()
    }

    protected open fun initDagger() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }
}
