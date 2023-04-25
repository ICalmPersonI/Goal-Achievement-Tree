package com.calmperson.goalachievementtree.model.fractaltree

import com.calmperson.goalachievementtree.model.ModelContract
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject

class FractalTreeSerializer @Inject constructor() : ModelContract.FractalTreeSerializer {

    override fun serialize(root: FractalTreeNode): ByteArray? {
        val bos = ByteArrayOutputStream()
        try {
            ObjectOutputStream(bos).use { oos ->
                oos.writeObject(root)
                return bos.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bos.close()
        }
        return null
    }

    override fun deserialize(bytes: ByteArray): FractalTreeNode? {
        val bis = ByteArrayInputStream(bytes)
        try {
            ObjectInputStream(bis).use { ois ->
                return ois.readObject() as FractalTreeNode
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bis.close()
        }
        return null
    }
    
}