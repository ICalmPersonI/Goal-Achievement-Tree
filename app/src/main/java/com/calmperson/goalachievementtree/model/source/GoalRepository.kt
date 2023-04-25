package com.calmperson.goalachievementtree.model.source

import androidx.lifecycle.LiveData
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.source.local.AppDatabase
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import javax.inject.Inject

class GoalRepository @Inject constructor(private val dataBase: AppDatabase) : ModelContract.GoalRepository {
    override fun getAll(): LiveData<List<Goal>> {
        return dataBase.goalDao().getAll()
    }

    override suspend fun insert(goal: Goal): Int {
        return dataBase.goalDao().insert(goal).toInt()
    }

    override suspend fun insetAll(goals: List<Goal>) {
        dataBase.goalDao().insertAll(*goals.toTypedArray())
    }

    override suspend fun update(goal: Goal) {
        dataBase.goalDao().update(goal)
    }

    override suspend fun updateAll(goals: List<Goal>) {
        dataBase.goalDao().updateAll(*goals.toTypedArray())
    }

    override suspend fun delete(goal: Goal) {
        dataBase.goalDao().delete(goal)
    }
}