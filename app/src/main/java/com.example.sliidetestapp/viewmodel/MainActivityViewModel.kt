package com.example.sliidetestapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sliidetestapp.Event
import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.usecases.AddUserUseCase
import com.example.sliidetestapp.usecases.DeleteUserUseCase
import com.example.sliidetestapp.usecases.GetUsersUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase,
                                                private val addUserUseCase: AddUserUseCase,
                                                private val deleteUserUseCase: DeleteUserUseCase
  ): ViewModel() {


    private val usersMutableData = mutableStateListOf<UserModel>()
    val users: List<UserModel> = usersMutableData
    private val toastTextMutableData = MutableLiveData<Event<Throwable>>()
    val toastText: LiveData<Event<Throwable>> = toastTextMutableData

    fun getUsers() {
       getUsersUseCase.invoke()
               .subscribe(
                   { usersMutableData.add(it) },
                   { toastTextMutableData.postValue(Event(it)) }
               )
    }

    fun addUser(name: String, email: String) {
        addUserUseCase.invoke(name = name, email = email)
            .subscribe(
                { usersMutableData.add(it) },
                { toastTextMutableData.postValue(Event(it)) }
            )
    }

    fun deleteUser(userId: String) {
        deleteUserUseCase.invoke(userId = userId)
            .subscribe(
                { usersMutableData.removeAll { it.id == userId } },
                { toastTextMutableData.postValue(Event(it)) }
            )
    }
}
