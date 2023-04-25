package com.calmperson.goalachievementtree.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.*
import com.calmperson.goalachievementtree.InvalidTreeDepthException
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeGenerator
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import com.calmperson.goalachievementtree.model.goal.Step
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val treeGenerator: ModelContract.FractalTreeGenerator,
    private val treeDrawer: ModelContract.FractalTreeDrawer,
    private val treeRepository: ModelContract.FractalTreeRepository,
    private val goalRepository: ModelContract.GoalRepository,
) : ViewModel() {

    companion object {
        private val TAG = MainActivityViewModel::class.java.simpleName
    }

    val goals: LiveData<List<Goal>> = goalRepository.getAll()
    val trees: LiveData<List<FractalTree>> = treeRepository.getAll()

    private val _currentGoal: MutableLiveData<Goal> = MutableLiveData()
    val currentGoal: LiveData<Goal>
        get() = _currentGoal

    fun updateGoal(goal: Goal) {
        CoroutineScope(Dispatchers.IO).launch {
            if (goals.isInitialized) {
                goalRepository.update(goal)
            }
        }
    }

    fun deleteGoal(goal: Goal) {
        if(_currentGoal.value == goal) {
            _currentGoal.value = null
        }
        CoroutineScope(Dispatchers.IO).launch {
            goalRepository.delete(goal)
            val tree = treeRepository.getTreeByGoalId(goal.id)
            treeRepository.delete(tree)
        }
    }

    fun createNewGoal(goal: String, steps: List<String>) {
        val tree = createNewTree()
        CoroutineScope(Dispatchers.IO).launch {
            val goalID = goalRepository.insert(Goal(goal, steps.map { Step(it, false) }))
            tree.goal_id = goalID
            treeRepository.insert(tree)
        }
    }

    fun setCurrentGoal(goal: Goal) {
        _currentGoal.value = goal
    }

    fun Goal.getTreeAsImageBitmap(): ImageBitmap {
        val treeDepth = this.steps.count(Step::isDone) + 1
        return findTreeByGoalID(this.id).asImageBitmap(treeDepth)
    }

    private fun findTreeByGoalID(id: Int): FractalTree {
        return try {
            trees.value!!.firstOrNull { it.goal_id == id }!!
        } catch (e: NullPointerException) {
            Log.wtf(TAG, "The tree was not found in the database")
            val tree = createNewTree().apply { goal_id = this.id }
            CoroutineScope(Dispatchers.IO).launch { treeRepository.insert(tree) }
            return tree
        }
    }

    private fun createNewTree(): FractalTree = treeGenerator.generate()

    private fun FractalTree.asImageBitmap(treeDepth: Int): ImageBitmap {
        if (treeDepth > FractalTreeGenerator.DEPTH) throw InvalidTreeDepthException()
        return treeDrawer.draw(this, treeDepth).asImageBitmap()
    }
}