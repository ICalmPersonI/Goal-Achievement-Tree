package com.calmperson.goalachievementtree.model.fractaltree

class FractalTreeNode(
    val startX: Float,
    val startY: Float,
    val endX: Float,
    val endY: Float,
    val angle: Float,
    val depth: Int,
    var left: FractalTreeNode? = null,
    var right: FractalTreeNode? = null,
) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FractalTreeNode

        if (startX != other.startX) return false
        if (startY != other.startY) return false
        if (endX != other.endX) return false
        if (endY != other.endY) return false
        if (angle != other.angle) return false
        if (depth != other.depth) return false
        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startX.hashCode()
        result = 31 * result + startY.hashCode()
        result = 31 * result + endX.hashCode()
        result = 31 * result + endY.hashCode()
        result = 31 * result + angle.hashCode()
        result = 31 * result + depth
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}