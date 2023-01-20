package com.example.sliidetestapp.usecases

import com.example.sliidetestapp.retrofit.UserClient
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Named

class DeleteUserUseCase @Inject constructor(@Named("AuthorizedClient") private val client: UserClient) {

    fun invoke(userId: String): Completable {
        return client.deleteUser(userId)
    }
}