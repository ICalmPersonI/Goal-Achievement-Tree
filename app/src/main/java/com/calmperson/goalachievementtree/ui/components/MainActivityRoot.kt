package com.calmperson.goalachievementtree.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calmperson.goalachievementtree.R
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import com.calmperson.goalachievementtree.ui.TestTag
import com.calmperson.goalachievementtree.ui.components.screen.*
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme


@Composable
fun MainActivityRoot(
    currentGoal: LiveData<Goal> = MutableLiveData(fakeGoal(0)),
    goals: LiveData<List<Goal>> = MutableLiveData(fakeGoals()),
    getTreeByGoal: (Goal) -> ImageBitmap = { fakeBitmap.asImageBitmap() },
    onGoalClick: (Goal) -> Unit = { },
    updateGoal: (Goal) -> Unit = { },
    onDeleteGoalButtonClick: (Goal) -> Unit = { },
    onConfirmButtonClick: (String, List<String>) -> Unit = { _, _ -> },
    onExitButtonClick: () -> Unit = { }
) {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val isDialogAboutAppVisible = remember { mutableStateOf(false) }
    val screens = listOf(
        Screen.StepsToGoal,
        Screen.Tree,
        Screen.GoalGrid
    )

    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier.height(70.dp),
                onAboutAppButtonClick = { isDialogAboutAppVisible.value = true },
                onExitButtonClick = onExitButtonClick
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (navBackStackEntry.value?.destination?.route == Screen.GoalGrid.route) {
                FloatingActionButton(
                    modifier = Modifier.testTag(TestTag.CREATE_NEW_GOAL_BUTTON),
                    onClick = {
                        navController.navigate(Screen.CreateGoal.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.height(100.dp),
                navController = navController,
                navBackStackEntry = navBackStackEntry,
                screens = screens
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.GoalGrid.route
        ) {
            composable(Screen.StepsToGoal.route) {
                StepsToGoalScreen(
                    goal = currentGoal,
                    onNavigate = {
                        navController.navigate(Screen.GoalGrid.route) {
                            popUpTo(Screen.GoalGrid.route)
                        }
                    },
                    updateGoal = updateGoal
                )
            }
            composable(Screen.Tree.route) {
                TreeScreen(
                    tree = if (currentGoal.value != null) getTreeByGoal(currentGoal.value!!) else null
                )
            }
            composable(Screen.GoalGrid.route) {
                GoalGridScreen(
                    goals = goals,
                    currentGoal = currentGoal,
                    getTreeByGoal = getTreeByGoal,
                    onGoalClick = onGoalClick,
                    onDeleteButtonClick = onDeleteGoalButtonClick
                )
            }
            composable(Screen.CreateGoal.route) {
                CreateGoalScreen(
                    onConfirmButtonClick = onConfirmButtonClick,
                    onNavigate = {
                        navController.navigate(Screen.GoalGrid.route) {
                            popUpTo(Screen.GoalGrid.route)
                        }
                    }
                )
            }
        }

        if (isDialogAboutAppVisible.value) {
            AboutAppDialog(isVisible = isDialogAboutAppVisible)
        }
    }

}

@Composable
private fun AboutAppDialog(
    modifier: Modifier = Modifier,
    isVisible: MutableState<Boolean>
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { isVisible.value = !isVisible.value },
        title = { Text(text = stringResource(R.string.about)) },
        text = { Text(text = stringResource(R.string.about_app)) },
        buttons = {
            TextButton(onClick = { isVisible.value = !isVisible.value }) {
                Text(text = stringResource(R.string.ok))

            }
        }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun Preview() {
    GoalAchievementTreeTheme {
        MainActivityRoot()
    }
}