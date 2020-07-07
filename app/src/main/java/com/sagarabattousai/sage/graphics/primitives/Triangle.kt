package com.sagarabattousai.sage.graphics.primitives

import android.content.res.Resources
import android.opengl.GLES30
import com.sagarabattousai.sage.R
import com.sagarabattousai.sage.graphics.Colour
import com.sagarabattousai.sage.graphics.toFloatBuffer
import java.nio.FloatBuffer


const val COORDS_PER_VERTEX = 3

const val cos30: Float = 0.86602540378f

var triangleCoords = floatArrayOf(
    0.0f, cos30 / 2, 0.0f,
    -0.5f, -cos30 / 2, 1.0f,
    0.5f, -cos30 / 2, 0.2f
)

class Triangle(resourcer: Resources) : Mesh(resourcer) {

    private val vertexShader = compileVertexShader(R.raw.triangle_vertex_shader)

    private val fragmentShader = compileFragmentShader(R.raw.triangle_frag_shader)

    private var program: Int = linkShaders(vertexShader, fragmentShader)

    private val colour = Colour(
        105,
        80,
        255,
        255
    ).toFloatArray()//floatArrayOf(0.6367f, 0.7695f, 0.222656f, 1.0f)

    private val vertexBuffer: FloatBuffer = triangleCoords.toFloatBuffer()

/*
    private var vboHandle: Int =
        IntArray(1).run {
            GLES30.glGenBuffers(1, this, 0)

            val vbo = this[0]

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)

            GLES30.glBufferData(vbo, triangleCoords.size * SIZEOF_FLOAT, vertexBuffer, GLES30.GL_DYNAMIC_DRAW)

            vbo
        }

 */


    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX

    private val vertexStride: Int = COORDS_PER_VERTEX * SIZEOF_FLOAT

    fun draw(mvpMatrix: FloatArray) {
        GLES30.glUseProgram(program)


        GLES30.glEnableVertexAttribArray(vPositionHandle)

        GLES30.glVertexAttribPointer(
            vPositionHandle,
            COORDS_PER_VERTEX,
            GLES30.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )

        GLES30.glUniform4fv(vColourHandle, 1, colour, 0)

        GLES30.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, mvpMatrix, 0)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)

        GLES30.glDisableVertexAttribArray(vPositionHandle)

    }

    companion object {
        private const val vPositionHandle: Int = 0
        private const val uMVPMatrixHandle: Int = 1
        private const val vColourHandle: Int = 0

    }


}