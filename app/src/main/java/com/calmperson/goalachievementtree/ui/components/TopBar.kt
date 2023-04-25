package com.calmperson.goalachievementtree.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.calmperson.goalachievementtree.R

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onAboutAppButtonClick: () -> Unit,
    onExitButtonClick: () -> Unit
) {
    val menuExpanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        modifier = modifier,
        actions = {
            IconButton(onClick = { menuExpanded.value = !menuExpanded.value }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded.value = !menuExpanded.value
                        onAboutAppButtonClick.invoke()
                    }
                ) {
                    Text(text = stringResource(id = R.string.about))
                }
                DropdownMenuItem(
                    onClick = {
                        menuExpanded.value = !menuExpanded.value
                        onExitButtonClick.invoke()
                    }
                ) {
                    Text(text = stringResource(R.string.exit))
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
}
