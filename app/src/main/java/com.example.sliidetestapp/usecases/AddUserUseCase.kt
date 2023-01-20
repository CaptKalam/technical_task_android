package com.example.sliidetestapp.usecases

import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.retrofit.UserClient
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class AddUserUseCase @Inject constructor(@Named("AuthorizedClient") private val client: UserClient) {

    //gender and status are required http request fields but the task description doesn't say anything about them.
    //That's why they are hardcoded
    fun invoke(name: String, email: String, gender: String = "male", status: String = "active") : Observable<UserModel> {
        return client.addUser(name, email, gender, status)
    }
}