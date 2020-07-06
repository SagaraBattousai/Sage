package com.sagarabattousai.sage.primitives

import android.content.res.Resources
import android.opengl.GLES30
import com.sagarabattousai.sage.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


const val COORDS_PER_VERTEX = 3
const val SIZEOF_FLOAT = 4

const val cos30: Float = 0.86602540378f

var triangleCoords = floatArrayOf(
     0.0f,  cos30/2, 0.0f,
    -1.0f, -cos30/2, 0.0f,
     1.0f, -cos30/2, 0.0f
)

class Triangle(resourcer: Resources): Mesh(resourcer) {

    private val vertexShaderCode = R.raw.triangle_vertex_shader

    private val fragmentShaderCode = R.raw.triangle_frag_shader

    private var program: Int

    private var vPMatrixHandle: Int = 0

    init {
        val vertexShader: Int = loadVertexShader(vertexShaderCode)

        val fragmentShader: Int = loadFragmentShader(fragmentShaderCode)

        program = GLES30.glCreateProgram().also {
            GLES30.glAttachShader(it, vertexShader)

            GLES30.glAttachShader(it, fragmentShader)

            GLES30.glLinkProgram(it)
        }
    }


    val colour = floatArrayOf(0.6367f, 0.7695f, 0.222656f, 1.0f)

    private var vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(triangleCoords.size * SIZEOF_FLOAT).run {
            order(ByteOrder.nativeOrder())

            asFloatBuffer().apply {
                put(triangleCoords)

                position(0)
            }
        }


    private var positionHandle: Int = 0

    private var colourHandle: Int = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX

    private val vertexStride: Int = COORDS_PER_VERTEX * SIZEOF_FLOAT

    fun draw(mvpMatrix: FloatArray) {
        GLES30.glUseProgram(program)

        positionHandle = GLES30.glGetAttribLocation(program, "vPosition").also {

            GLES30.glEnableVertexAttribArray(it)

            GLES30.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES30.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            colourHandle = GLES30.glGetUniformLocation(program, "vColour").also { cHandle ->
                GLES30.glUniform4fv(cHandle, 1, colour, 0)
            }

            vPMatrixHandle = GLES30.glGetUniformLocation(program, "uMVPMatrix")

            GLES30.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)

            GLES30.glDisableVertexAttribArray(it)
        }
    }




}