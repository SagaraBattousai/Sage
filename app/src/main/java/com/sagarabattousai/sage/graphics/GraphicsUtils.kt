package com.sagarabattousai.sage.graphics

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val SIZEOF_FLOAT = 4


fun FloatArray.toFloatBuffer(): FloatBuffer =

    ByteBuffer.allocateDirect(this.size * SIZEOF_FLOAT).let {
        it.order(ByteOrder.nativeOrder())

        it.asFloatBuffer().also { fb ->
            fb.put(this)

            fb.position(0)
        }
    }