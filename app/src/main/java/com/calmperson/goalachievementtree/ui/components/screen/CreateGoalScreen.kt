package com.calmperson.goalachievementtree.ui.components.screen


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.goalachievementtree.R
import com.calmperson.goalachievementtree.ui.TestTag
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme
import com.calmperson.goalachievementtree.ui.theme.NotoSansRegular
import org.burnoutcrew.reorderable.*
import java.util.UUID

@Composable
fun CreateGoalScreen(
    modifier: Modifier = Modifier,
    onConfirmButtonClick: (String, List<String>) -> Unit,
    onNavigate: () -> Unit
) {
    var goal by remember { mutableStateOf("") }
    var step by remember { mutableStateOf("") }
    val steps = remember { mutableStateListOf<Pair<UUID, String>>() }
    val stepsCount = "${steps.size}/10"

    Column(
        modifier = modifier.padding(10.dp),
    ) {

        Box(
            modifier = Modifier.height(65.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = stringResource(R.string.create_goal),
                fontSize = 30.sp,
                fontFamily = NotoSansRegular
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .testTag(TestTag.ENTER_YOUR_GOAL)
                .height(65.dp)
                .fillMaxWidth(),
            value = goal,
            onValueChange = { goal = it },
            label = {
                Text(text = stringResource(R.string.your_goal))
            },
            singleLine = true
        )

        Box(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = stringResource(R.string.steps),
                fontSize = 21.sp,
                fontFamily = NotoSansRegular
            )
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = stepsCount,
                fontSize = 21.sp,
                fontFamily = NotoSansRegular
            )
        }

        StepsList(
            modifier = Modifier.weight(1f),
            steps = steps
        )

        Spacer(modifier = Modifier.height(10.dp).fillMaxWidth())

        OutlinedTextField(
            modifier = Modifier
                .testTag(TestTag.ENTER_STEP)
                .height(60.dp)
                .fillMaxWidth(),
            value = step,
            onValueChange = { step = it },
            enabled = steps.size < 10,
        )
        
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 3.dp, bottom = 3.dp)
        ) {
            val onClick: () -> Unit = {
                if (steps.size < 10) steps.add(Pair(UUID.randomUUID(), step)).also { step = "" }
                else onConfirmButtonClick(goal, steps.map { it.second }).also { onNavigate.invoke() }
            }
            TextButton(
                modifier = Modifier.fillMaxSize(),
                onClick = onClick
            ) {
                Text(
                    text = if (steps.size < 10) stringResource(R.string.add)
                    else stringResource(R.string.confirm)
                )
            }
        }
    }

}

@Composable
private fun StepsList(
    modifier: Modifier = Modifier,
    steps: SnapshotStateList<Pair<UUID, String>>
) {
    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            steps.apply {
                add(to.index, removeAt(from.index))
            }
        }
    )

    LazyColumn(
        modifier = modifier
            .reorderable(reorderableState)
            .detectReorderAfterLongPress(reorderableState),
        state = reorderableState.listState
    ) {
        items(steps, { it.first }) { item ->
            ReorderableItem(
                modifier = Modifier.padding(top = 3.dp, end = 3.dp),
                reorderableState = reorderableState,
                key = item.first
            ) { isDragging ->
                val scale = animateFloatAsState(if (isDragging) 0.98f else 0f)
                val shape = when(item.first) {
                    steps.first().first -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    steps.last().first -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    else -> RoundedCornerShape(0.dp)
                }
                StepContainer(
                    modifier = Modifier
                        .heightIn(min = 50.dp)
                        .border(5.dp, Color.LightGray, shape)
                        .graphicsLayer {
                            if (isDragging) {
                                this.scaleX = scale.value
                                this.scaleY = scale.value
                            }
                        }
                        .clickable { steps.remove(item) },
                    step = item.second
                )
            }
        }
    }
}

@Composable
private fun StepContainer(
    modifier: Modifier = Modifier,
    step: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
                .align(Alignment.CenterStart),
            text = step,
            fontSize = 14.sp
        )
    }
}



@Preview(showBackground = true)
@Composable
fun CreateGoalPreview() {
    GoalAchievementTreeTheme {
        CreateGoalScreen(
            onConfirmButtonClick = { _, _ -> },
            onNavigate = { }
        )
    }
}

@Preview(
    showBackground = true,
    heightDp = 500,
    widthDp = 300
)
@Composable
fun CreateGoalPreviewSmallScreen() {
    GoalAchievementTreeTheme {
        CreateGoalScreen(
            onConfirmButtonClick = { _, _ -> },
            onNavigate = { }
        )
    }
}