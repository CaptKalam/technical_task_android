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
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
internal class GetUsersUseCaseTest : RxUnitTest() {

    @Mock
    lateinit var userClient: UserClient

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        getUsersUseCase = GetUsersUseCase(userClient)
    }

    @Test
    fun `SHOULD return users WHEN network call was successful`() {
       whenever(userClient.getUsers()).thenReturn(
            just(
                listOf(
                    UserModel(id = "1", name = "name 1", email = "email 1"),
                    UserModel(id = "2", name = "name 2", email = "email 2"),
                    UserModel(id = "3", name = "name 3", email = "email 3"),
                    UserModel(id = "4", name = "name 4", email = "email 4"),
                )
            )
        )

        getUsersUseCase.invoke()
            .test()
            .assertValues(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2"),
                UserModel(id = "3", name = "name 3", email = "email 3"),
                UserModel(id = "4", name = "name 4", email = "email 4")
            )
            .assertComplete()
    }

    @Test
    fun `SHOULD return error WHEN network call is unsuccessful`() {
        val error = Throwable("test error")
        whenever(userClient.getUsers()).thenReturn(Observable.error(error))

        getUsersUseCase.invoke()
            .test()
            .assertError(error)
    }
}