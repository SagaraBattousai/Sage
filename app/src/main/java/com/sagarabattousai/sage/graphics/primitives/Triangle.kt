package com.sagarabattousai.sage.graphics.primitives

import android.opengl.GLES30
import com.sagarabattousai.sage.R
import com.sagarabattousai.sage.graphics.Colour
import com.sagarabattousai.sage.graphics.SIZEOF_FLOAT
import com.sagarabattousai.sage.graphics.resourcers.ShaderResourcer
import com.sagarabattousai.sage.graphics.shaders.Shader
import com.sagarabattousai.sage.graphics.shaders.Shader.ShaderBuilder
import com.sagarabattousai.sage.graphics.toFloatBuffer


var triangleCoords = floatArrayOf(
    0.0f, 10.0f, 0.0f,
    -10.0f, -10.0f, 0.0f,
    10.0f, -10.0f, 0.0f
)

@Suppress("PropertyName", "PrivatePropertyName")
class Triangle(resourcer: ShaderResourcer) {

    private val program: Shader = ShaderBuilder(resourcer)
        .withVertexShader(R.raw.triangle_vertex_shader)
        .withFragmentShader(R.raw.triangle_frag_shader)
        .build()


    private val colour = Colour(105, 80, 255, 255).toFloatArray()

    private val handles = IntArray(3)

    private val VBO: Int = with(handles) {
        GLES30.glGenBuffers(1, this, VBO_OFFSET)
        this[VBO_OFFSET]
    }

    private val VAO: Int = with(handles) {
        GLES30.glGenVertexArrays(1, this, VAO_OFFSET)
        this[VAO_OFFSET]
    }

    /*
    private val EBO: Int = with(handles) {
        GLES30.glGenBuffers(1, this, EBO_OFFSET)
        this[EBO_OFFSET]
    }
    */

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX


    init {
        GLES30.glBindVertexArray(VAO)
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO)
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER, triangleCoords.size * SIZEOF_FLOAT,
            triangleCoords.toFloatBuffer(), GLES30.GL_DYNAMIC_DRAW
        )

        GLES30.glVertexAttribPointer(
            vPositionHandle,
            COORDS_PER_VERTEX,
            GLES30.GL_FLOAT,
            false,
            VERTEX_STRIDE,
            0
        )

        GLES30.glEnableVertexAttribArray(vPositionHandle)

        GLES30.glBindVertexArray(0) //Unbind VAO
    }


    fun draw(modelMatrix: FloatArray, viewMatrix: FloatArray, projectionMatrix: FloatArray) {

        program.use()

        GLES30.glBindVertexArray(VAO)

        GLES30.glUniform4fv(vColourHandle, 1, colour, 0)

        GLES30.glUniformMatrix4fv(viewHandle, 1, false, viewMatrix, 0)

        GLES30.glUniformMatrix4fv(projectionHandle, 1, false, projectionMatrix, 0)

        GLES30.glUniformMatrix4fv(modelHandle, 1, false, modelMatrix, 0)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)

        GLES30.glBindVertexArray(0)

        //Do I still need?
        GLES30.glDisableVertexAttribArray(vPositionHandle)


    }

    companion object {
        private const val vPositionHandle: Int = 0
        private const val modelHandle: Int = 1
        private const val viewHandle: Int = 2
        private const val projectionHandle: Int = 3
        private const val vColourHandle: Int = 0
        private const val VBO_OFFSET: Int = 0
        private const val VAO_OFFSET: Int = 1

        //private const val EBO_OFFSET: Int = 2
        private const val COORDS_PER_VERTEX = 3
        private const val VERTEX_STRIDE: Int = COORDS_PER_VERTEX * SIZEOF_FLOAT


    }
}