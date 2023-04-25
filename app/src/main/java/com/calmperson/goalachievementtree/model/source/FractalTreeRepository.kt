package com.calmperson.goalachievementtree.model.source

import androidx.lifecycle.LiveData
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.source.local.AppDatabase
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import javax.inject.Inject

class FractalTreeRepository @Inject constructor(private val dataBase: AppDatabase) :
    ModelContract.FractalTreeRepository {

    override fun getAll(): LiveData<List<FractalTree>>  {
        return dataBase.fractalTreeDao().getAll()
    }

    override fun getTreeByGoalId(id: Int): FractalTree {
        return dataBase.fractalTreeDao().getTreeByGoalID(id)
    }

    override suspend fun insert(tree: FractalTree) {
        dataBase.fractalTreeDao().insert(tree)
    }

    override suspend fun insetAll(trees: List<FractalTree>) {
        dataBase.fractalTreeDao().insert(*trees.toTypedArray())
    }

    override suspend fun delete(tree: FractalTree) {
        dataBase.fractalTreeDao().delete(tree)
    }

}