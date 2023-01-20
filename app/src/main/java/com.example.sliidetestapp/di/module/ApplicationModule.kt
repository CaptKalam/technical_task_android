package com.example.sliidetestapp.di.module

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import com.example.sliidetestapp.di.qualifier.ApplicationContext
import com.example.sliidetestapp.di.scope.ApplicationScope
import com.example.sliidetestapp.network.AuthorizationInterceptor
import com.example.sliidetestapp.network.LastPageInterceptor
import com.example.sliidetestapp.retrofit.UserClient
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
class ApplicationModule(private val application: Application) {

  @Provides
  @ApplicationScope
  @ApplicationContext
  fun applicationContext(): Context = application.applicationContext

  @Provides
  @ApplicationScope
  fun provideConnectivityManager(): ConnectivityManager {
    return applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }

  @Provides
  @ApplicationScope
  fun providePackageManager(): PackageManager {
    return applicationContext().packageManager
  }

  @Provides
  @ApplicationScope
  fun provideContentResolver(): ContentResolver {
    return applicationContext().contentResolver
  }

  @Provides
  @ApplicationScope
  @Named("UserOkHttpClient")
  fun provideUserOkHttpClient() : OkHttpClient =
    OkHttpClient
      .Builder()
      .addInterceptor(LastPageInterceptor())
      .build()

  @Provides
  @ApplicationScope
  @Named("AuthorizedOkHttpClient")
  fun provideAuthorizedOkHttpClient() : OkHttpClient =
    OkHttpClient
      .Builder()
      .addInterceptor(AuthorizationInterceptor())
      .build()

  @Provides
  @ApplicationScope
  @Named("UserRetrofitClient")
  fun provideUserRetrofitClient(@Named("UserOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://gorest.co.in/")
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .build()
  }

  @Provides
  @ApplicationScope
  @Named("UserClient")
  fun provideUserClient(@Named("UserRetrofitClient") retrofit: Retrofit): UserClient = retrofit.create(UserClient::class.java)

  @Provides
  @ApplicationScope
  @Named("AuthorizedRetrofitClient")
  fun provideAuthorizedRetrofitClient(@Named("AuthorizedOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://gorest.co.in/")
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .build()
  }

  @Provides
  @ApplicationScope
  @Named("AuthorizedClient")
  fun provideAuthorizedClient(@Named("AuthorizedRetrofitClient") retrofit: Retrofit): UserClient = retrofit.create(UserClient::class.java)

}
