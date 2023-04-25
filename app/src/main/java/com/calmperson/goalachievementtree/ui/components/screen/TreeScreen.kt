package com.calmperson.goalachievementtree.ui.components.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calmperson.goalachievementtree.R
import com.calmperson.goalachievementtree.ui.TestTag
import com.calmperson.goalachievementtree.ui.components.fakeBitmap
import com.calmperson.goalachievementtree.ui.theme.GoalAchievementTreeTheme
import com.calmperson.goalachievementtree.ui.theme.NotoSansRegular

@Composable
fun TreeScreen(
    modifier: Modifier = Modifier,
    tree: ImageBitmap?
) {

    if (tree != null) {

        val treeState by remember { mutableStateOf(tree) }

        Column(modifier = modifier.padding(10.dp)) {
            Box(
                modifier = Modifier.height(65.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    text = stringResource(R.string.your_tree),
                    fontSize = 30.sp,
                    fontFamily = NotoSansRegular
                )
            }
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .testTag(TestTag.TREE)
                        .align(Alignment.Center),
                    bitmap = treeState,
                    contentDescription = null,
                )
            }
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

@Preview(showBackground = true)
@Composable
fun TreeScreenPreview() {
    GoalAchievementTreeTheme {
        TreeScreen(tree = fakeBitmap.asImageBitmap())
    }
}

@Preview(showBackground = true)
@Composable
fun TreeScreenPreviewTreeIsNull() {
    GoalAchievementTreeTheme {
        TreeScreen(tree = null)
    }
}