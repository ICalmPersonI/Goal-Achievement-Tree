package com.calmperson.goalachievementtree.model

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeNode
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import com.calmperson.goalachievementtree.model.source.local.goal.Goal

interface ModelContract {

    interface FractalTreeDrawer {
        fun draw(tree: FractalTree, maxDepthToDraw: Int): Bitmap
    }

    interface FractalTreeGenerator {
        fun generate(): FractalTree
    }

    interface FractalTreeSerializer {
        fun serialize(root: FractalTreeNode): ByteArray?
        fun deserialize(bytes: ByteArray): FractalTreeNode?
    }

    interface FractalTreeRepository {
        fun getAll(): LiveData<List<FractalTree>>
        fun getTreeByGoalId(id: Int): FractalTree
        suspend fun insert(tree: FractalTree)
        suspend fun insetAll(trees: List<FractalTree>)
        suspend fun delete(tree: FractalTree)
    }

    interface GoalRepository {
        fun getAll(): LiveData<List<Goal>>
        suspend fun insert(goal: Goal): Int
        suspend fun insetAll(goals: List<Goal>)
        suspend fun update(goal: Goal)
        suspend fun updateAll(goals: List<Goal>)
        suspend fun delete(goal: Goal)
    }

}