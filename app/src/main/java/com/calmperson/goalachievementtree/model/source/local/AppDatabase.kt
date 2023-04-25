package com.calmperson.goalachievementtree.model.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTreeDao
import com.calmperson.goalachievementtree.model.source.local.goal.Goal
import com.calmperson.goalachievementtree.model.source.local.goal.GoalDao
import com.calmperson.goalachievementtree.model.source.local.goal.StepsListConverter

@Database(entities = [Goal::class, FractalTree::class], version = 1)
@TypeConverters(StepsListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun fractalTreeDao(): FractalTreeDao
    abstract fun goalDao(): GoalDao

}