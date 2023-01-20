package com.example.sliidetestapp.retrofit

import com.example.sliidetestapp.model.UserModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface UserClient {

    @GET("/public/v2/users")
    fun getUsers(): Observable<List<UserModel>>

    @DELETE("/public/v2/users/{id}")
    fun deleteUser(@Path("id") userId: String): Completable

    @POST("/public/v2/users")
    fun addUser(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("gender") gender: String,
        @Query("status") status: String
    ): Observable<UserModel>
}