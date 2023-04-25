package com.calmperson.goalachievementtree.ui.components.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import com.calmperson.goalachievementtree.ui.components.fakeBitmap
import com.calmperson.goalachievementtree.ui.components.fakeGoals
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme
import com.calmperson.goalachievementtree.ui.theme.Mint
import com.calmperson.goalachievementtree.ui.theme.NotoSansRegular

@Composable
fun GoalGridScreen(
    modifier: Modifier = Modifier,
    goals: LiveData<List<Goal>>,
    currentGoal: LiveData<Goal>,
    getTreeByGoal: (Goal) -> ImageBitmap,
    onGoalClick: (Goal) -> Unit,
    onDeleteButtonClick: (Goal) -> Unit,
) {

    val currentGoalState = currentGoal.observeAsState()
    val goalsState = goals.observeAsState()

    if (goals.value != null) {
        Column(
            modifier = modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier.height(65.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    text = stringResource(R.string.your_goals),
                    fontSize = 30.sp,
                    fontFamily = NotoSansRegular
                )
            }
            GoalsGrid(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                goalsState = goalsState,
                currentGoalState = currentGoalState,
                onGoalClick = onGoalClick,
                onDeleteButtonClick = onDeleteButtonClick,
                getTreeByGoal = getTreeByGoal
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.you_do_not_have_any_goals)
            )
        }
    }
}

@Composable
private fun GoalsGrid(
    modifier: Modifier = Modifier,
    goalsState: State<List<Goal>?>,
    currentGoalState: State<Goal?>,
    onGoalClick: (Goal) -> Unit,
    onDeleteButtonClick: (Goal) -> Unit,
    getTreeByGoal: (Goal) -> ImageBitmap,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(goalsState.value!!) { goal ->
            val borderColor = if (currentGoalState.value == goal) Mint else MaterialTheme.colors.surface
            val isAchieved = goal.steps.all(Step::isDone)
            Box(
                modifier = Modifier
            ) {
                GoalContainer(
                    modifier = Modifier
                        .testTag(TestTag.GOAL)
                        .padding(15.dp)
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .clickable {
                            onGoalClick.invoke(goal)
                        },
                    goal = goal,
                    borderColor = borderColor,
                    getTreeByGoal = getTreeByGoal
                )
                IconButton(
                    modifier = Modifier
                        .testTag(TestTag.DELETE_GOAL_BUTTON)
                        .size(45.dp)
                        .align(Alignment.TopEnd),
                    onClick = { onDeleteButtonClick.invoke(goal) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = null
                    )
                }
                if (isAchieved) {
                    Image(
                        modifier = Modifier
                            .testTag(TestTag.ACHIEVED_GOAL_MARK)
                            .size(35.dp)
                            .align(Alignment.TopStart),
                        painter = painterResource(R.drawable.done),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun GoalContainer(
    modifier: Modifier = Modifier,
    goal: Goal,
    borderColor: Color,
    getTreeByGoal: (Goal) -> ImageBitmap,
) {

    Surface(
        modifier = modifier,
        border = BorderStroke(5.dp, borderColor),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                bitmap = getTreeByGoal.invoke(goal),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .height(30.dp)
                    .padding(start = 5.dp, end = 5.dp)
                    .fillMaxWidth(),
                text = goal.name,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GoalGridPreview() {
    GoalAchievementTreeTheme {
        GoalGridScreen(
            goals = MutableLiveData(fakeGoals()),
            currentGoal = MutableLiveData(),
            onGoalClick = { },
            onDeleteButtonClick = {},
            getTreeByGoal = { fakeBitmap.asImageBitmap() }
        )
    }
}