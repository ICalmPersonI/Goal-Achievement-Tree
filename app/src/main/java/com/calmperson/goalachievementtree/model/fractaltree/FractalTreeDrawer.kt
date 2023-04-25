package com.calmperson.goalachievementtree.model.fractaltree

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import com.calmperson.goalachievementtree.R
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.source.local.fractaltree.FractalTree

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FractalTreeDrawer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serializer: ModelContract.FractalTreeSerializer
    ) :
    ModelContract.FractalTreeDrawer {

    companion object {
        private const val STROKE_WIDTH_MULTIPLIER = 3f
        private const val TRUNK_GROWTH_ANGEL = 90f
        private const val BITMAP_HEIGHT = 1200
        private const val BITMAP_WIDTH = 1200
    }

    private val leafs = listOf(
        context.getDrawable(R.drawable.leaf)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf1)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf2)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf3)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf4)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf5)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf6)!!.toBitmap(),
        context.getDrawable(R.drawable.leaf7)!!.toBitmap()
    )

    private val flippedLeafs = listOf(
        leafs[0].flip(),
        leafs[1].flip(),
        leafs[2].flip(),
        leafs[3].flip(),
        leafs[4].flip(),
        leafs[5].flip(),
        leafs[6].flip(),
        leafs[7].flip()
    )

    private val plantPot = context.getDrawable(R.drawable.plant_pot)!!.toBitmap()

    private val bark = BitmapShader(
        context.getDrawable(R.drawable.bark_texture)!!.toBitmap(),
        Shader.TileMode.REPEAT,
        Shader.TileMode.REPEAT
    )

    override fun draw(tree: FractalTree, maxDepthToDraw: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(BITMAP_WIDTH, BITMAP_HEIGHT, Bitmap.Config.ARGB_8888)
        val root = serializer.deserialize(tree.root)!!
        val initialStrokeWidth = maxDepthToDraw.toFloat()
        val strokeWidth = initialStrokeWidth * STROKE_WIDTH_MULTIPLIER

        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            this.strokeWidth = strokeWidth
            shader = bark
        }

        val rootPoint = PointF(
            (bitmap.width / 2).toFloat(),
            (bitmap.height - 25).toFloat()
        )
        with(canvas) {
            drawBranch(root, rootPoint, paint)

            drawTree(root.left, strokeWidth - STROKE_WIDTH_MULTIPLIER, paint, maxDepthToDraw, rootPoint)
            drawTree(root.right, strokeWidth - STROKE_WIDTH_MULTIPLIER, paint, maxDepthToDraw, rootPoint)

            drawPlantPot(root, rootPoint)
        }

        return bitmap
    }

    private fun Canvas.drawTree(node: FractalTreeNode?, strokeWidth: Float, paint: Paint, maxDepthToDraw: Int, root: PointF) {
        if (node == null) return

        paint.strokeWidth = strokeWidth

        with(node) {
            if ((left == null && right == null) || depth == maxDepthToDraw) drawLeaf(this, root)
            else drawBranch(this, root, paint)

            if (depth == maxDepthToDraw) return

            drawTree(left, strokeWidth - STROKE_WIDTH_MULTIPLIER, paint, maxDepthToDraw, root)
            drawTree(right, strokeWidth - STROKE_WIDTH_MULTIPLIER, paint, maxDepthToDraw, root)
        }
    }

    private fun Canvas.drawBranch(node: FractalTreeNode, root: PointF, paint: Paint) {
        with(node) {
            drawLine(
                startX + root.x , startY + root.y,
                endX + root.x , endY + root.y,
                paint
            )
        }
    }

    private fun Canvas.drawLeaf(node: FractalTreeNode, root: PointF) {
        with(node) {
            var rect: RectF
            val degrees = angle - TRUNK_GROWTH_ANGEL
            rotate(startX + root.x, startY + root.y, degrees) {
                rect = RectF(
                    startX + root.x, startY + root.y - 20,
                    startX + root.x + 20, startY + root.y
                )
                drawBitmap(leafs.random(), null, rect, null)
                rect = RectF(
                    startX + root.x - 20, startY + root.y - 20,
                    startX + root.x, startY + root.y
                )
                drawBitmap(flippedLeafs.random(), null, rect, null)
            }
        }
    }

    private fun Canvas.drawPlantPot(node: FractalTreeNode, root: PointF) {
        with(node) {
            val rect = RectF(
                startX + root.x - 70 , startY + root.y - 100,
                startX + root.x + 70 , startY + root.y + 10
            )
            drawBitmap(plantPot, null, rect, null)
        }
    }

    private fun Canvas.rotate(x: Float, y: Float, degrees: Float, block: () -> Unit) {
        save(); rotate(degrees, x, y); block.invoke(); restore()
    }

    private fun Bitmap.flip(): Bitmap {
        val matrix = Matrix().apply { preScale(-1f, 1f) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun Drawable.toBitmap(): Bitmap = Bitmap.createBitmap(
        this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888
    ).apply {
        val canvas = Canvas(this)
        this@toBitmap.setBounds(0, 0, canvas.width, canvas.height)
        this@toBitmap.draw(canvas)
    }

}