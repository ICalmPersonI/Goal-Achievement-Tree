package com.calmperson.goalachievementtree

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeDrawer
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeGenerator
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeNode
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeSerializer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FractalTreeDrawerTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val serializer: ModelContract.FractalTreeSerializer = FractalTreeSerializer()
    private val generator: ModelContract.FractalTreeGenerator = FractalTreeGenerator(serializer)
    private val drawer: ModelContract.FractalTreeDrawer = FractalTreeDrawer(context, serializer)

    @Test
    fun testDrawTree() {
        val tree = generator.generate()
        val bitmapWithTree = drawer.draw(tree,  FractalTreeGenerator.DEPTH)
        val rootPoint = PointF(
            (bitmapWithTree.width / 2).toFloat(),
            (bitmapWithTree.height - 25).toFloat()
        )
        inspectBitmap(serializer.deserialize(tree.root), bitmapWithTree, rootPoint)
    }

    private fun inspectBitmap(node: FractalTreeNode?, bitmap: Bitmap, root: PointF) {
        if (node == null) return


        val cases = listOf(
            Color.TRANSPARENT == bitmap.getPixel((node.startX + root.x).toInt(), (node.startY + root.y).toInt()),
            Color.TRANSPARENT == bitmap.getPixel((node.endX + root.x).toInt(), (node.endY + root.y).toInt()),
            Color.TRANSPARENT == bitmap.getPixel((node.startX + root.x + 1).toInt(), (node.startY + root.y + 1).toInt()),
            Color.TRANSPARENT == bitmap.getPixel((node.endX + root.x + 1).toInt(), (node.endY + root.y + 1).toInt()),
            Color.TRANSPARENT == bitmap.getPixel((node.startX + root.x - 1).toInt(), (node.startY + root.y - 1).toInt()),
            Color.TRANSPARENT == bitmap.getPixel((node.endX + root.x - 1).toInt(), (node.endY + root.y - 1).toInt()),

        )

        Assert.assertFalse(cases.reduce { f, s -> f && s })

        inspectBitmap(node.right, bitmap, root)
        inspectBitmap(node.left, bitmap, root)
    }

}