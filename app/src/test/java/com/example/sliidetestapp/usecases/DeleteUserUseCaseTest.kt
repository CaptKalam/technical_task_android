package com.example.sliidetestapp.usecases

import com.example.sliidetestapp.RxUnitTest
import com.example.sliidetestapp.retrofit.UserClient
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
internal class DeleteUserUseCaseTest : RxUnitTest() {

    @Mock
    lateinit var userClient: UserClient

    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun setup() {
        deleteUserUseCase = DeleteUserUseCase(userClient)
    }

    @Test
    fun `SHOULD complete WHEN network call was successful`() {
        val id = "test id"

        whenever(userClient.deleteUser(userId = id)).thenReturn(Completable.complete())

        deleteUserUseCase.invoke(userId = id)
            .test()
            .assertComplete()
    }

    @Test
    fun `SHOULD return error WHEN network call was unsuccessful`() {
        val error = Throwable("test message")

        whenever(userClient.deleteUser(userId = any())).thenReturn(Completable.error(error))

        deleteUserUseCase.invoke(userId = "1234")
            .test()
            .assertError(error)
    }
}