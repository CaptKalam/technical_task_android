package com.example.sliidetestapp.network

import com.example.sliidetestapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + BuildConfig.API_TOKEN)
            .build()
        return chain.proceed(request)
    }
}