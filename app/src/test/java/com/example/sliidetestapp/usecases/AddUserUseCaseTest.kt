package com.example.sliidetestapp.usecases

import com.example.sliidetestapp.RxUnitTest
import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.retrofit.UserClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.just
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
internal class AddUserUseCaseTest : RxUnitTest() {

    @Mock
    lateinit var userClient: UserClient

    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setup() {
        addUserUseCase = AddUserUseCase(userClient)
    }

    @Test
    fun `SHOULD return user WHEN network call was successful`() {
        val name = "test name"
        val email = "test email"
        val id = "123"

        whenever(userClient.addUser(name = name, email = email, gender = "male", status = "active"))
            .thenReturn(just(UserModel(id, name, email)))

        addUserUseCase.invoke(name = name, email = email)
            .test()
            .assertValue(UserModel(id = id, name = name, email = email))
            .assertComplete()
    }

    @Test
    fun `SHOULD return user WHEN network call with all parameters was successful`() {
        val id = "1234"
        val name = "test name"
        val email = "test email"
        val gender = "female"
        val status = "inactive"

        whenever(userClient.addUser(name = name, email = email, gender = gender, status = status))
            .thenReturn(just(UserModel(id, name, email)))

        addUserUseCase.invoke(name = name, email = email, gender = gender, status = status)
            .test()
            .assertValue(UserModel(id = id, name = name, email = email))
            .assertComplete()
    }

    @Test
    fun `SHOULD return error WHEN network call is unsuccessful`() {
        val error = Throwable("test error")
        whenever(userClient.addUser(any(), any(), any(), any())).thenReturn(Observable.error(error))

        addUserUseCase.invoke("test name", "test email")
            .test()
            .assertError(error)
    }
}