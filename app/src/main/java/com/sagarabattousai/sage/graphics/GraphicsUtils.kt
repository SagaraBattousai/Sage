package com.sagarabattousai.sage.graphics

import android.opengl.GLES30
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.reflect.KProperty

const val SIZEOF_FLOAT = 4
const val SIZEOF_INT = 4


fun FloatArray.toFloatBuffer(): FloatBuffer =

    ByteBuffer.allocateDirect(this.size * SIZEOF_FLOAT).let {
        it.order(ByteOrder.nativeOrder())

        it.asFloatBuffer().also { fb ->
            fb.put(this)

            fb.position(0)
        }
    }

fun IntArray.toIntBuffer(): IntBuffer =

    ByteBuffer.allocateDirect(this.size * SIZEOF_INT).let {
        it.order(ByteOrder.nativeOrder())

        it.asIntBuffer().also { ib ->
            ib.put(this)

            ib.position(0)
        }
    }

fun glGetShaderiv(shader: Int, pname: Int): Int =
    IntArray(1).let { arr ->
        GLES30.glGetShaderiv(shader, pname, arr, 0)
        return arr[0]
    }

fun glGetProgramiv(shader: Int, pname: Int): Int =
    IntArray(1).let { arr ->
        GLES30.glGetProgramiv(shader, pname, arr, 0)
        return arr[0]
    }

/*
//Could use arrays later to get all three buffers at once (VBO, VAO, EBO) (VAO is different call)
class BufferDelegater {
    private var bufferID: Int? = null

    operator fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return bufferID ?: genBufferID()
    }

    private fun genBufferID(): Int = with(IntArray(1)) {
        GLES30.glGenBuffers(1, this, 0)
        bufferID = this[0]
        this[0]
    }
}

 */