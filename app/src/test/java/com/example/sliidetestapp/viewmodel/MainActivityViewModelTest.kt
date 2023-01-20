package com.example.sliidetestapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sliidetestapp.RxUnitTest
import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.usecases.AddUserUseCase
import com.example.sliidetestapp.usecases.DeleteUserUseCase
import com.example.sliidetestapp.usecases.GetUsersUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
internal class MainActivityViewModelTest : RxUnitTest() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getUsersUseCase: GetUsersUseCase
    @Mock
    private lateinit var deleteUserUseCase: DeleteUserUseCase
    @Mock
    private lateinit var addUserUseCase: AddUserUseCase

    private lateinit var mainActivityViewModel: MainActivityViewModel

    @Before
    fun setup() {
        mainActivityViewModel = MainActivityViewModel(
            getUsersUseCase = getUsersUseCase,
            addUserUseCase = addUserUseCase,
            deleteUserUseCase = deleteUserUseCase
        )
    }

    @Test
    fun `SHOULD add users WHEN get users is called`() {
        whenever(getUsersUseCase.invoke()).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2")
            )
        )

        mainActivityViewModel.getUsers()

        assertThat(mainActivityViewModel.users).containsExactly(
            UserModel(id = "1", name = "name 1", email = "email 1"),
            UserModel(id = "2", name = "name 2", email = "email 2")
        )
    }

    @Test
    fun `SHOULD return toast text WHEN get users return error`() {
        val message = "test message"
        whenever(getUsersUseCase.invoke()).thenReturn(Observable.error(Throwable(message)))

        mainActivityViewModel.getUsers()

        assertThat(mainActivityViewModel.toastText.value).isEqualTo(message)
    }

    @Test
    fun `SHOULD add user WHEN add users is called`() {
        whenever(addUserUseCase.invoke(name = "name 1", email = "email 1")).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
            )
        )

        mainActivityViewModel.addUser(name = "name 1", email = "email 1")

        assertThat(mainActivityViewModel.users).containsExactly(
            UserModel(id = "1", name = "name 1", email = "email 1")
        )
    }

    @Test
    fun `SHOULD NOT add user WHEN use case returns error`() {
        whenever(addUserUseCase.invoke(any(), any(), any(), any())).thenReturn(
            Observable.error(Throwable("test message"))
        )

        mainActivityViewModel.addUser(name = "name 1", email = "email 1")

        assertThat(mainActivityViewModel.users).isEmpty()
    }

    @Test
    fun `SHOULD return toast text WHEN add user return error`() {
        val message = "test message"
        whenever(addUserUseCase.invoke(any(), any(), any(), any())).thenReturn(Observable.error(Throwable(message)))

        mainActivityViewModel.addUser(name = "name 1", email = "email 1")

        assertThat(mainActivityViewModel.toastText.value).isEqualTo(message)
    }

    @Test
    fun `SHOULD remove user WHEN user exists`() {
        whenever(getUsersUseCase.invoke()).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2")
            )
        )
        whenever(deleteUserUseCase.invoke("1")) .thenReturn(Completable.complete())

        mainActivityViewModel.getUsers()
        mainActivityViewModel.deleteUser("1")

        assertThat(mainActivityViewModel.users).containsExactly(UserModel(id = "2", name = "name 2", email = "email 2"))
    }

    @Test
    fun `SHOULD NOT remove user WHEN user does not exist`() {
        whenever(getUsersUseCase.invoke()).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2")
            )
        )
        whenever(deleteUserUseCase.invoke("3")) .thenReturn(Completable.complete())

        mainActivityViewModel.getUsers()
        mainActivityViewModel.deleteUser("3")

        assertThat(mainActivityViewModel.users).containsExactly(
            UserModel(id = "1", name = "name 1", email = "email 1"),
            UserModel(id = "2", name = "name 2", email = "email 2")
        )
    }

    @Test
    fun `SHOULD NOT remove user WHEN use case returns error`() {
        whenever(getUsersUseCase.invoke()).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2")
            )
        )
        whenever(deleteUserUseCase.invoke(any())) .thenReturn(Completable.error(Throwable("test message")))

        mainActivityViewModel.getUsers()
        mainActivityViewModel.deleteUser("1")

        assertThat(mainActivityViewModel.users).containsExactly(
            UserModel(id = "1", name = "name 1", email = "email 1"),
            UserModel(id = "2", name = "name 2", email = "email 2")
        )
    }

    @Test
    fun `SHOULD remove user WHEN use case returns error`() {
        whenever(getUsersUseCase.invoke()).thenReturn(
            Observable.just(
                UserModel(id = "1", name = "name 1", email = "email 1"),
                UserModel(id = "2", name = "name 2", email = "email 2")
            )
        )
        whenever(deleteUserUseCase.invoke(any())) .thenReturn(Completable.error(Throwable("test message")))

        mainActivityViewModel.getUsers()
        mainActivityViewModel.deleteUser("1")

        assertThat(mainActivityViewModel.users).containsExactly(
            UserModel(id = "1", name = "name 1", email = "email 1"),
            UserModel(id = "2", name = "name 2", email = "email 2")
        )
    }

    @Test
    fun `SHOULD return toast text WHEN delete user returns error`() {
        val message = "test message"
        whenever(deleteUserUseCase.invoke(any())).thenReturn(Completable.error(Throwable(message)))

        mainActivityViewModel.deleteUser("1")

        assertThat(mainActivityViewModel.toastText.value).isEqualTo(message)
    }

}