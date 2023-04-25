package com.calmperson.goalachievementtree.model.source.local.goal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.calmperson.goalachievementtree.model.goal.Step

@Entity(tableName = "goal")
class Goal(
    val name: String,
    val steps: List<Step>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Goal

        if (name != other.name) return false
        if (steps != other.steps) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + steps.hashCode()
        return result
    }
}
