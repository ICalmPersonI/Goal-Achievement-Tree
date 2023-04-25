package com.calmperson.goalachievementtree.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.calmperson.goalachievementtree.model.goal.Step
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import com.calmperson.goalachievementtree.model.source.local.goal.Goal

@Composable
fun fakeGoal(id: Int): Goal = Goal(
    "Do it!",
    listOf(
        Step("One", false),
        Step("Two", false),
        Step("Three", true),
        Step(
            "Creating the NavHost requires the NavController previously created via rememberNavController()" +
                    " and the route of the starting destination of your graph. NavHost creation uses the lambda" +
                    " syntax from the Navigation Kotlin DSL to construct your navigation graph.",
            true
        )
    ),
    id
)

@Composable
fun fakeBitmap(id: Int): Bitmap =
    BitmapFactory.decodeResource(LocalContext.current.applicationContext.resources, id)

val fakeBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

val fakeTree = FractalTree(
    ByteArray(1)
)


@Composable
fun fakeGoals(): List<Goal> {
    return listOf(
        fakeGoal(0),
        fakeGoal(1),
        fakeGoal(2),
        fakeGoal(3),
        fakeGoal(4),
        fakeGoal(5),
        fakeGoal(6),
    )
}