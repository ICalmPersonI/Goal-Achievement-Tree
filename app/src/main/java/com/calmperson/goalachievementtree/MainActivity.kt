package com.calmperson.goalachievementtree

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.calmperson.goalachievementtree.ui.components.MainActivityRoot
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme
import com.calmperson.goalachievementtree.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.goals.observe(this) {
            Log.e("Test", "Data has been updated")
        }
        viewModel.trees.observe(this) { }

        setContent {
            GoalAchievementTreeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainActivityRoot(
                        currentGoal = viewModel.currentGoal,
                        goals = viewModel.goals,
                        getTreeByGoal = { goal -> with(viewModel) { goal.getTreeAsImageBitmap() } },
                        onGoalClick = { goal -> viewModel.setCurrentGoal(goal) },
                        updateGoal = { goal -> viewModel.updateGoal(goal) },
                        onDeleteGoalButtonClick = {goal -> viewModel.deleteGoal(goal) },
                        onConfirmButtonClick = { goal, steps -> viewModel.createNewGoal(goal, steps) },
                        onExitButtonClick = { finishAndRemoveTask() }
                    )
                }
            }
        }
    }
}