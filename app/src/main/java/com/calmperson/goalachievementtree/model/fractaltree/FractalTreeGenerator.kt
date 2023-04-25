package com.calmperson.goalachievementtree.model.fractaltree

import android.graphics.PointF
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class FractalTreeGenerator
@Inject constructor(private val serializer: ModelContract.FractalTreeSerializer) :
    ModelContract.FractalTreeGenerator {

    companion object {
        const val ANGLE = 90f // The angle at which the tree begins to grow.
        const val DEPTH = 11
        const val MIN_BRANCH_LENGTH_MULTIPLIER = 5
        const val MAX_BRANCH_LENGTH_MULTIPLIER = 20
        const val MIN_DEVIATION = 15 // The minimum possible deviation from the previous angle.
        const val MAX_DEVIATION = 30 // The maximum possible deviation from the previous angle.
    }

    override fun generate(): FractalTree {
        val start = PointF(0f, 0f)
        val depth = 1
        val end = nextPoint(start, ANGLE, depth)
        val root = FractalTreeNode(start.x, start.y, end.x, end.y, ANGLE, depth)
        growTree(end, root, depth)
        return FractalTree(serializer.serialize(root)!!)
    }

    private fun growTree(start: PointF, node: FractalTreeNode, depth: Int) {
        if (depth == DEPTH) return

        val angle = node.angle
        val end = nextPoint(start, angle, depth)
        node.left = FractalTreeNode(start.x, start.y, end.x, end.y, angle - Random.nextInt(MIN_DEVIATION, MAX_DEVIATION), depth)
        node.right = FractalTreeNode(start.x, start.y, end.x, end.y, angle + Random.nextInt(MIN_DEVIATION, MAX_DEVIATION), depth)

        growTree(end, node.left!!, depth + 1)
        growTree(end, node.right!!, depth + 1)
    }

    private fun nextPoint(start: PointF, angle: Float, depth: Int): PointF {
        val branchLength = Random.nextInt(MIN_BRANCH_LENGTH_MULTIPLIER, MAX_BRANCH_LENGTH_MULTIPLIER)
        val x = start.x + (cos(Math.toRadians(angle.toDouble())) * (depth - DEPTH) * branchLength).toFloat()
        val y = start.y + (sin(Math.toRadians(angle.toDouble())) * (depth - DEPTH) * branchLength).toFloat()
        return PointF(x, y)
    }

}