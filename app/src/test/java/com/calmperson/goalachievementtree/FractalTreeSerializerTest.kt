package com.calmperson.goalachievementtree

import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeNode
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeSerializer
import org.junit.Assert
import org.junit.Test

class FractalTreeSerializerTest {

    private val serializer: ModelContract.FractalTreeSerializer = FractalTreeSerializer()

    @Test
    fun testSerializeAndDeserialize() {
        val fractalTree = FractalTreeNode(0f, 0f, 1f, 1f, 90f, 10)
        val serialized = serializer.serialize(fractalTree)
        Assert.assertEquals(fractalTree, serializer.deserialize(serialized!!))
    }

}