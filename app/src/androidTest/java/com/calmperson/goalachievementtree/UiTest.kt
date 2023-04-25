package com.calmperson.goalachievementtree

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.goalachievementtree.model.goal.Step
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import com.calmperson.goalachievementtree.ui.TestTag
import com.calmperson.goalachievementtree.ui.components.screen.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val hilt = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val goals = List(2) { index ->
        Goal(
            "Goal Number $index",
            List(10) { _ -> Step("Some text", false) }
        )
    }

    @Test
    fun createGoalScreen() {

        with(composeRule) {
            onNodeWithTag(Screen.GoalGrid.route).performClick()
            onNodeWithTag(TestTag.CREATE_NEW_GOAL_BUTTON).performClick()
            onNodeWithTag(TestTag.ENTER_YOUR_GOAL).performTextInput("Test")
            for (i in (1..10)) {
                onNodeWithTag(TestTag.ENTER_STEP).performTextInput("Some step")
                onNodeWithText(context.getString(R.string.add)).performClick()
            }
            onNodeWithText(context.getString(R.string.confirm)).performClick()
            onAllNodesWithTag(TestTag.GOAL).assertCountEquals(1)
        }

    }

    @Test
    fun goalsGridScreen() {
        addGoals()
        with(composeRule) {
            onAllNodesWithTag(TestTag.GOAL).assertCountEquals(2)
            onAllNodesWithTag(TestTag.GOAL).onFirst().performClick()
            onNodeWithTag(Screen.StepsToGoal.route).performClick()
            val steps = onAllNodesWithTag(TestTag.STEP_CONTAINER)
            for (i in 0 until 10) {
                if (i == 5) onNodeWithTag(TestTag.STEPS_LIST).performTouchInput { swipeUp(durationMillis = 1000) }
                steps[i].performClick()
            }
            onNodeWithTag(TestTag.CONGRATULATION_DIALOG).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.ok)).performClick()
            onNodeWithTag(TestTag.ACHIEVED_GOAL_MARK).assertExists()
            onNodeWithText("Test 0").assertExists()
            onAllNodesWithTag(TestTag.DELETE_GOAL_BUTTON).onFirst().performClick()
            onNodeWithText("Test 0").assertDoesNotExist()
        }
    }

    @Test
    fun stepsToGoalScreen() {
        addGoals()
        with(composeRule) {
            val goalContainers = onAllNodesWithTag(TestTag.GOAL)
            assert(goalContainers.fetchSemanticsNodes().size == 2)
            goalContainers[0].performClick()
            onNodeWithTag(Screen.StepsToGoal.route).performClick()
            val steps = onAllNodesWithTag(TestTag.STEP_CONTAINER)
            for (i in 0 until 10) {
                if (i == 5) onNodeWithTag(TestTag.STEPS_LIST).performTouchInput { swipeUp(durationMillis = 1000) }
                steps[i].performClick()
            }
            onNodeWithTag(TestTag.CONGRATULATION_DIALOG).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.ok)).performClick()
        }
    }

    @Test
    fun treeScreen() {
        with(composeRule) {
            onNodeWithTag(Screen.Tree.route).performClick()
            onNodeWithText(context.getString(R.string.you_have_not_selected_created_any_goals_yet)).assertExists()
            onNodeWithTag(Screen.StepsToGoal.route).performClick()
            onNodeWithText(context.getString(R.string.you_have_not_selected_created_any_goals_yet)).assertExists()
            onNodeWithTag(Screen.GoalGrid.route).performClick()
            addGoals()
            onAllNodesWithTag(TestTag.GOAL).onFirst().performClick()
            onNodeWithTag(Screen.Tree.route).performClick()
            onNodeWithTag(TestTag.TREE).assertExists()
            onNodeWithTag(Screen.StepsToGoal.route).performClick()
            onNodeWithText(context.getString(R.string.you_have_not_selected_created_any_goals_yet)).assertDoesNotExist()
        }

    }

    private fun addGoals() {
        with(composeRule) {
            goals.forEachIndexed { index, goal ->
                onNodeWithTag(Screen.GoalGrid.route).performClick()
                onNodeWithTag(TestTag.CREATE_NEW_GOAL_BUTTON).performClick()
                onNodeWithTag(TestTag.ENTER_YOUR_GOAL).performTextInput("Test $index")
                for (step in goal.steps) {
                    onNodeWithTag(TestTag.ENTER_STEP).performTextInput(step.text)
                    onNodeWithText(context.getString(R.string.add)).performClick()
                }
                onNodeWithText(context.getString(R.string.confirm)).performClick()
            }
        }
    }


}