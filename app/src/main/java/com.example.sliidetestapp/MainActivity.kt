package com.example.sliidetestapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.sliidetestapp.composable.UsersListComposable
import com.example.sliidetestapp.di.component.ActivityComponent
import com.example.sliidetestapp.di.component.DaggerActivityComponent
import com.example.sliidetestapp.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private val viewModel by lazy { getViewModelInstance(MainActivityViewModel::class) }

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .applicationComponent(applicationComponent)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)


        viewModel.toastText.observe (this) { error ->
            if (!error.hasBeenHandled) {
                Toast.makeText(
                    this,
                    this.getString(R.string.toast_error, error.getContentIfNotHandled()?.message ?: ""),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        setContent {
            MaterialTheme {
                UsersListComposable(
                    viewModel = viewModel,
                    onAddUserClick = { name, email -> viewModel.addUser(name, email) },
                    onRemoveUserClick = { user -> viewModel.deleteUser(userId = user.id) }
                )
            }
        }

        //don't perform network call when view is recreated
        if(savedInstanceState == null)
            viewModel.getUsers()
    }
}