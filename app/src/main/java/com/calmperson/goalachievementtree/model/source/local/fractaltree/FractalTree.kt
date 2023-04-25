package com.calmperson.goalachievementtree.model.source.local.fractaltree

import androidx.room.*

@Entity(tableName = "fractal_tree")
class FractalTree(
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val root: ByteArray,
    var goal_id: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FractalTree

        if (!root.contentEquals(other.root)) return false
        if (goal_id != other.goal_id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = root.contentHashCode()
        result = 31 * result + goal_id
        return result
    }
}


