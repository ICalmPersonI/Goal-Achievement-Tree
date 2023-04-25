package com.calmperson.goalachievementtree

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeGenerator
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeNode
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeSerializer
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FractalTreeGeneratorTest {

    private val serializer: ModelContract.FractalTreeSerializer = FractalTreeSerializer()
    private val generator: ModelContract.FractalTreeGenerator = FractalTreeGenerator(serializer)

    @Test
    fun testGenerate() {
        val root = serializer.deserialize(generator.generate().root)!!
        val expectedDepth = 0
        traversal(root, null, expectedDepth)
    }

    private fun traversal(node: FractalTreeNode?, lastNode: FractalTreeNode?, expectedDepth: Int) {
        if (node == null) return

        if (lastNode != null) {
            assertEquals(expectedDepth, node.depth)
            assertEquals(lastNode.endX, node.startX)
            assertEquals(lastNode.endY, node.startY)
            assertTrue(lastNode.angle != node.angle)
        }

        assertNotEquals(node, node.left)
        assertNotEquals(node, node.right)

        traversal(node.left, node, expectedDepth + 1)
        traversal(node.right, node, expectedDepth + 1)
    }

}