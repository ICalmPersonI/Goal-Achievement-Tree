package com.calmperson.goalachievementtree.ui.components.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.calmperson.goalachievementtree.R
import com.calmperson.goalachievementtree.model.goal.Step
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import com.calmperson.goalachievementtree.ui.TestTag
import com.calmperson.goalachievementtree.ui.components.fakeGoal
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme
import com.calmperson.goalachievementtree.ui.theme.Mint
import com.calmperson.goalachievementtree.ui.theme.NotoSansRegular

@Composable
fun StepsToGoalScreen(
    modifier: Modifier = Modifier,
    goal: LiveData<Goal>,
    updateGoal: (Goal) -> Unit,
    onNavigate: () -> Unit
) {
    if (goal.value != null) {

        var completedSteps by remember { mutableStateOf(goal.value!!.steps.count(Step::isDone)) }

        val isGoalAchieved = remember(completedSteps) {
            mutableStateOf(completedSteps == 10)
        }
        val isVisibleCongratulationDialog = remember(isGoalAchieved) {
            mutableStateOf(isGoalAchieved.value)
        }

        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier.height(65.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    text = stringResource(R.string.your_goal),
                    fontSize = 30.sp,
                    fontFamily = NotoSansRegular
                )
            }

            GoalContainer(
                modifier = Modifier.height(65.dp),
                goalName = goal.value!!.name
            )

            Box(
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.CenterStart),
                    text = stringResource(R.string.steps),
                    fontSize = 21.sp,
                    fontFamily = NotoSansRegular
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.CenterEnd),
                    text = stringResource(R.string.completed, completedSteps),
                    fontSize = 14.sp,
                    fontFamily = NotoSansRegular
                )
            }

            StepsList(
                modifier = Modifier.weight(1f),
                steps = goal.value!!.steps,
                enable = !isGoalAchieved.value,
                onStepClick = {
                    completedSteps = goal.value!!.steps.count(Step::isDone)
                    updateGoal.invoke(goal.value!!)
                }
            )

        }

        if (isVisibleCongratulationDialog.value) {
            CongratulationDialog(
                modifier = Modifier,
                isVisible = isVisibleCongratulationDialog,
                onOkButtonClick = onNavigate
            )
        }

    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.you_have_not_selected_created_any_goals_yet)
            )
        }
    }
}

@Composable
private fun StepsList(
    modifier: Modifier = Modifier,
    steps: List<Step>,
    enable: Boolean,
    onStepClick: () -> Unit
) {
    Column(
        modifier = modifier
            .testTag(TestTag.STEPS_LIST)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        steps.forEachIndexed { index, step ->
            val backgroundColor = MaterialTheme.colors.background
            val containerColor = remember { mutableStateOf(if (step.isDone) Mint else backgroundColor) }
            val shape = when(index) {
                0 -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                steps.lastIndex -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                else -> RoundedCornerShape(0.dp)
            }
            StepContainer(
                modifier = Modifier
                    .testTag(TestTag.STEP_CONTAINER)
                    .heightIn(min = 50.dp),
                text = step.text,
                color = containerColor.value,
                shape = shape,
                onClick = {
                    if (enable) {
                        step.isDone = !step.isDone
                        containerColor.value = if (step.isDone) Mint else backgroundColor
                        onStepClick.invoke()
                    }
                }
            )
        }
    }
}

@Composable
private fun GoalContainer(
    modifier: Modifier = Modifier,
    goalName: String
) {
    Surface(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = goalName,
                fontSize = 24.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

        }
    }
}

@Composable
private fun StepContainer(
    modifier: Modifier,
    text: String,
    color: Color,
    shape: RoundedCornerShape,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        shape = shape,
        color = color,
        border = BorderStroke(5.dp, MaterialTheme.colors.surface),
    ) {
        Box(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(10.dp),
                text = text,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun CongratulationDialog(
    modifier: Modifier = Modifier,
    isVisible: MutableState<Boolean>,
    onOkButtonClick: () -> Unit
) {
    AlertDialog(
        modifier = modifier
            .testTag(TestTag.CONGRATULATION_DIALOG),
        onDismissRequest = {
            isVisible.value = false
        },
        title = {
            Text(
                text = stringResource(R.string.congratulations)
            )
        },
        text = {
            Text(
                text = stringResource(R.string.you_have_achieved_your_goal_and_grown_a_tree)
            )
        },
        buttons = {
            TextButton(
                onClick = {
                    isVisible.value = false
                    onOkButtonClick.invoke()
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun StepsToGoalScreenPreview() {
    GoalAchievementTreeTheme {
        StepsToGoalScreen(
            goal = MutableLiveData(fakeGoal(id = 1)),
            onNavigate = { },
            updateGoal = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StepsToGoalScreenPreviewGoalIsNull() {
    GoalAchievementTreeTheme {
        StepsToGoalScreen(
            goal = MutableLiveData(),
            onNavigate = { },
            updateGoal = { },
        )
    }
}