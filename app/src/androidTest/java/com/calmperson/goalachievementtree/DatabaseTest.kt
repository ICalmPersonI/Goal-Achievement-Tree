package com.calmperson.goalachievementtree

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeGenerator
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeSerializer
import com.calmperson.goalachievementtree.model.goal.Step
import com.calmperson.goalachievementtree.model.source.local.AppDatabase
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTreeDao
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import com.calmperson.goalachievementtree.model.source.local.goal.GoalDao
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class DatabaseTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fractalTreeSerializer: ModelContract.FractalTreeSerializer = FractalTreeSerializer()
    private val fractalTreeGenerator: ModelContract.FractalTreeGenerator =
        FractalTreeGenerator(fractalTreeSerializer)

    private lateinit var goalDao: GoalDao
    private lateinit var treeDao: FractalTreeDao
    private lateinit var db: AppDatabase

    private val goals = List(10) { goalNumber ->
        Goal(
            "Goal №$goalNumber",
            List(10) { index -> Step(index.toString(), false) }
        )
    }

    private val trees = List(10) { _ -> fractalTreeGenerator.generate() }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        goalDao = db.goalDao()
        treeDao = db.fractalTreeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun testInsert_GoalDao() {
        fillDb()

        val receivedGoals = goalDao.getAll().test().awaitValue()

        Assert.assertEquals(goals.size, receivedGoals.value().size)
        Assert.assertArrayEquals(
            goals.toTypedArray(),
            receivedGoals.value().map { Goal(it.name, it.steps, 0) }.toTypedArray()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun testUpdateAll_GoalDao() = runTest {
        fillDb()

        val updatedGoals = goalDao.getAll().test().awaitValue().value().map {
            it.steps.forEach { step -> step.isDone = Random.nextInt(0, 100) > 100 }
            Goal(
                it.name + " Updated",
                it.steps,
                it.id
            )
        }.toList()
        goalDao.updateAll(*updatedGoals.toTypedArray())
        val receivedGoals = goalDao.getAll().test().awaitValue()

        Assert.assertEquals(updatedGoals.size, receivedGoals.value().size)
        Assert.assertArrayEquals(
            updatedGoals.toTypedArray(),
            receivedGoals.value().toTypedArray()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun testUpdate_GoalDao() = runTest {
        fillDb()

        val updatedGoal = goalDao.getAll().test().awaitValue().value()[0]
            .apply { this.steps[5].isDone = true }
        goalDao.update(updatedGoal)
        val receivedGoal = goalDao.getAll().test().awaitValue().value()[0]

        Assert.assertEquals(updatedGoal, receivedGoal)
    }

    @Test
    @Throws(IOException::class)
    fun testDelete_GoalDao() {
        fillDb()

        val goalToDelete = goalDao.getAll().test().awaitValue().value()[5]
        goalDao.delete(goalToDelete)
        val receivedGoals = goalDao.getAll().test().awaitValue()

        Assert.assertEquals(goals.size - 1, receivedGoals.value().size)
    }

    @Test
    @Throws(IOException::class)
    fun testDelete_TreeDao() {
        fillDb()

        val treeToDelete = treeDao.getAll().test().awaitValue().value()[3]
        treeDao.delete(treeToDelete)
        val receivedTrees = treeDao.getAll().test().awaitValue()

        Assert.assertEquals(trees.size - 1, receivedTrees.value().size)
    }

    @Test
    @Throws(IOException::class)
    fun testGetTreeByGoalID_TreeDao() {
        fillDb()

        val receivedTree = treeDao.getTreeByGoalID(2)

        Assert.assertEquals(trees[1], receivedTree)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fillDb() = runTest {
        goals.zip(trees).forEach { (goal, tree) ->
            val id = goalDao.insert(goal)
            tree.goal_id = id.toInt()
            treeDao.insert(tree)
        }
    }


}