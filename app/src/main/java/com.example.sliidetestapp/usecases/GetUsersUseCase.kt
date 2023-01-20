package com.example.sliidetestapp.usecases

import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.retrofit.UserClient
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class GetUsersUseCase @Inject constructor(@Named("UserClient") private val client: UserClient) {

    fun invoke(): Observable<UserModel> {
        return client.getUsers()
            .concatMapIterable { it }
    }
}