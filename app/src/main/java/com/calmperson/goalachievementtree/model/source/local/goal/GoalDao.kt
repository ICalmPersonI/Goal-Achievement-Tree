package com.calmperson.goalachievementtree.model.source.local.goal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GoalDao {

    @Query("SELECT * FROM goal")
    fun getAll(): LiveData<List<Goal>>

    @Insert
    suspend fun insert(goal: Goal): Long

    @Insert
    suspend fun insertAll(vararg goal: Goal)

    @Update
    suspend fun update(goal: Goal)

    @Update
    suspend fun updateAll(vararg goal: Goal)

    @Delete
    suspend fun delete(goal: Goal)
}