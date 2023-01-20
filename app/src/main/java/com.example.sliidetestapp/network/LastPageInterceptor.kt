package com.example.sliidetestapp.network

import okhttp3.Interceptor
import okhttp3.Response

private const val PAGE_PARAM = "page"

class LastPageInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val request = chain.request()
        return if(request.url.queryParameter(PAGE_PARAM).isNullOrEmpty()) {
            val numberOfPages = response.header("X-Pagination-Pages") ?: "1"
            response.close()
            val url = request.url.newBuilder().addQueryParameter(PAGE_PARAM, numberOfPages).build()
            chain.proceed(request.newBuilder().url(url).build())
        } else {
            chain.proceed(request)
        }
    }
}