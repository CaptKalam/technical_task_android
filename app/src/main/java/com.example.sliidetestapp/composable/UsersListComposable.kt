package com.example.sliidetestapp.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sliidetestapp.R
import com.example.sliidetestapp.model.UserModel
import com.example.sliidetestapp.viewmodel.MainActivityViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsersListComposable(
    viewModel: MainActivityViewModel,
    onAddUserClick: (String, String) -> Unit,
    onRemoveUserClick: (UserModel) -> Unit,
    modifier: Modifier = Modifier) {

    val showAddUserDialog = remember { mutableStateOf(false) }
    val showRemoveUserDialog = remember { mutableStateOf(false) }
    val removingUser: UserModel? = null
    var userToRemove by remember { mutableStateOf(removingUser) }

    val userName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val users = viewModel.users

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            items(users) { user ->
                run {
                    Column(modifier = modifier
                        .padding(all = 8.dp)
                        .combinedClickable(
                            onLongClick = {
                                showRemoveUserDialog.value = true
                                userToRemove = user
                            },
                            onClick = { }
                        )) {
                        Row {
                            Text(text = user.name)
                        }
                        Row {
                            Text(text = user.email)

                        }
                    }
                }
            }
        }

        ExtendedFloatingActionButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = { showAddUserDialog.value = true },
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_user_text)
                )
            },
            text = { Text(text = stringResource(R.string.add_user_text)) }
        )

        if(showRemoveUserDialog.value) {
            RemoveUserAlertDialog(showRemoveUserDialog, userToRemove, onRemoveUserClick)
        }

        if (showAddUserDialog.value) {
            AddUserAlertDialog(showAddUserDialog, onAddUserClick, userName, email)
        }
    }
}

@Composable
private fun AddUserAlertDialog(
    showAddUserDialog: MutableState<Boolean>,
    onAddUserClick: (String, String) -> Unit,
    userName: MutableState<String>,
    email: MutableState<String>
) {
    AlertDialog(
        onDismissRequest = { showAddUserDialog.value = false },
        confirmButton = {
            TextButton(onClick = {
                onAddUserClick(userName.value, email.value)
                    .also { showAddUserDialog.value = false }
            }) {
                Text(text = stringResource(R.string.add_user_text))
            }
        },
        dismissButton = {
            TextButton(onClick = { showAddUserDialog.value = false }) {
                Text(text = stringResource(R.string.cancel_text))
            }
        },
        title = {
            Text(text = stringResource(R.string.add_user_text))
        },
        text = {
            Column {
                Text(text = stringResource(R.string.user_name_label))
                TextField(
                    value = userName.value,
                    onValueChange = { userName.value = it }
                )
                Text(text = stringResource(R.string.email_label))
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it }
                )
            }
        },
    )
}

@Composable
private fun RemoveUserAlertDialog(
    showRemoveUserDialog: MutableState<Boolean>,
    userToRemove: UserModel?,
    onRemoveUserClick: (UserModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = { showRemoveUserDialog.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    userToRemove?.let {
                        onRemoveUserClick(it)
                    }.also { showRemoveUserDialog.value = false }
                },
            ) {
                Text(text = stringResource(R.string.remove_text))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { showRemoveUserDialog.value = false }) {
                Text(text = stringResource(id = R.string.cancel_text))
            }
        },
        title = {
            Text(text = stringResource(R.string.remove_user_title))
        },
        text = {
            Text(text = stringResource(R.string.remove_user_confirmation_question))
        }
    )
}

@Preview
@Composable
fun RemoveUserAlertDialogPreview() {
    RemoveUserAlertDialog(
        showRemoveUserDialog = remember { mutableStateOf(true) },
        userToRemove = UserModel(id = "1", name = "name", email = "email"),
        onRemoveUserClick = { }
    )
}

@Preview
@Composable
fun AddUserAlertDialogPreview() {
    AddUserAlertDialog(
        showAddUserDialog = remember { mutableStateOf(true) } ,
        onAddUserClick = { _, _ -> },
        userName = remember { mutableStateOf("name") },
        email = remember { mutableStateOf("email") }
    )
}

