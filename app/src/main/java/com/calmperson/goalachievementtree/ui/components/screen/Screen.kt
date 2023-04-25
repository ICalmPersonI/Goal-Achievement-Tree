package com.calmperson.goalachievementtree.ui.components.screen

import androidx.annotation.DrawableRes
import com.calmperson.goalachievementtree.R

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object GoalGrid : Screen("root/goal_grid", R.drawable.grid)
    object StepsToGoal : Screen("root/steps_to_goal", R.drawable.list)
    object Tree : Screen("root/tree", R.drawable.leaf)
    object CreateGoal: Screen("root/create_goal", -1)
}
