package com.calmperson.goalachievementtree.model.source.local.fractaltree

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FractalTreeDao {

    @Query("SELECT * FROM fractal_tree")
    fun getAll(): LiveData<List<FractalTree>>

    @Query("SELECT * FROM fractal_tree WHERE fractal_tree.goal_id = :id")
    fun getTreeByGoalID(id: Int): FractalTree

    @Insert
    suspend fun insert(vararg tree: FractalTree)

    @Delete
    fun delete(tree: FractalTree)


}