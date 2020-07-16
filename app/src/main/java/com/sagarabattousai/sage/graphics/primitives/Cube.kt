package com.sagarabattousai.sage.graphics.primitives

import android.opengl.GLES30
import com.sagarabattousai.sage.R
import com.sagarabattousai.sage.graphics.Colour
import com.sagarabattousai.sage.graphics.SIZEOF_FLOAT
import com.sagarabattousai.sage.graphics.resourcers.ShaderResourcer
import com.sagarabattousai.sage.graphics.shaders.Shader
import com.sagarabattousai.sage.graphics.toFloatBuffer
import com.sagarabattousai.sage.graphics.toIntBuffer


/*

      E-------H
    / |      /|
   /  |     / |
   A-------D  |
   |  F----|--G
   | /     | /
   |/      |/
   B-------C

*/

val cubeCoords = floatArrayOf(
    -1.0f, 1.0f, 1.0f, //A
    -1.0f, -1.0f, 1.0f, //B
    1.0f, -1.0f, 1.0f, //C
    1.0f, 1.0f, 1.0f, //D

    -1.0f, 1.0f, -1.0f, //E
    -1.0f, -1.0f, -1.0f, //F
    1.0f, -1.0f, -1.0f, //G
    1.0f, 1.0f, -1.0f, //H

    -1.0f, 1.0f, 1.0f, //A
    -1.0f, 1.0f, -1.0f, //E
    -1.0f, -1.0f, -1.0f, //F
    -1.0f, -1.0f, 1.0f, //B

    1.0f, 1.0f, 1.0f, //D
    1.0f, -1.0f, 1.0f, //C
    1.0f, -1.0f, -1.0f, //G
    1.0f, 1.0f, -1.0f, //H

    -1.0f, 1.0f, 1.0f, //A
    -1.0f, 1.0f, -1.0f, //E
    1.0f, 1.0f, -1.0f, //H
    1.0f, 1.0f, 1.0f, //D


    -1.0f, -1.0f, 1.0f, //B
    -1.0f, -1.0f, -1.0f, //F
    1.0f, -1.0f, -1.0f, //G
    1.0f, -1.0f, 1.0f  //C
)


private const val A = 0
private const val B = 1
private const val C = 2
private const val D = 3
private const val E = 4
private const val F = 5
private const val G = 6
private const val H = 7

/*

      E-------H
    / |      /|
   /  |     / |
   A-------D  |
   |  F----|--G
   | /     | /
   |/      |/
   B-------C

*/

val cubeIndices = intArrayOf(
    A, B, C,
    A, C, D,

    D, C, G,
    G, H, D,

    H, G, F,
    H, F, E,

    E, F, A,
    A, B, F,

    E, A, D,
    D, H, E,

    F, G, C,
    C, B, F
)

/*

      E-------H
    / |      /|
   /  |     / |
   A-------D  |
   |  F----|--G
   | /     | /
   |/      |/
   B-------C

*/

val normalCoords = floatArrayOf(
    0.0f, 0.0f, 1.0f, //A
    0.0f, 0.0f, 1.0f, //B
    0.0f, 0.0f, 1.0f, //C
    0.0f, 0.0f, 1.0f, //D

    0.0f, 0.0f, -1.0f, //E
    0.0f, 0.0f, -1.0f, //F
    0.0f, 0.0f, -1.0f, //G
    0.0f, 0.0f, -1.0f, //H

    -1.0f, 0.0f, 0.0f, //A
    -1.0f, 0.0f, 0.0f, //E
    -1.0f, 0.0f, 0.0f, //F
    -1.0f, 0.0f, 0.0f, //B

    1.0f, 0.0f, 0.0f, //D
    1.0f, 0.0f, 0.0f, //C
    1.0f, 0.0f, 0.0f, //G
    1.0f, 0.0f, 0.0f, //H

    0.0f, 1.0f, 0.0f, //A
    0.0f, 1.0f, 0.0f, //E
    0.0f, 1.0f, 0.0f, //H
    0.0f, 1.0f, 0.0f, //D


    0.0f, -1.0f, 0.0f, //B
    0.0f, -1.0f, 0.0f, //F
    0.0f, -1.0f, 0.0f, //G
    0.0f, -1.0f, 0.0f  //C
)

@Suppress("PropertyName", "PrivatePropertyName")
class Cube(resourcer: ShaderResourcer) {


    private val program: Shader = Shader.ShaderBuilder(resourcer)
        .withVertexShader(R.raw.triangle_vertex_shader)
        .withFragmentShader(R.raw.triangle_frag_shader)
        .build()


    private val colour = Colour(255, 128, 79, 255).toFloatArray()

    private val handles = IntArray(5)

    private val VBO: Int = with(handles) {
        GLES30.glGenBuffers(1, this, VBO_OFFSET)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, this[VBO_OFFSET])
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER, cubeCoords.size * SIZEOF_FLOAT,
            cubeCoords.toFloatBuffer(), GLES30.GL_DYNAMIC_DRAW
        )

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

        this[VBO_OFFSET]
    }

    private val normalVBO: Int = with(handles) {
        GLES30.glGenBuffers(1, this, NORMAL_VBO_OFFSET)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, this[NORMAL_VBO_OFFSET])
        GLES30.glBufferData(
            GLES30.GL_ARRAY_BUFFER, normalCoords.size * SIZEOF_FLOAT,
            normalCoords.toFloatBuffer(), GLES30.GL_STATIC_DRAW
        )

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

        this[NORMAL_VBO_OFFSET]
    }

    private val EBO: Int = with(handles) {
        GLES30.glGenBuffers(1, this, EBO_OFFSET)

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, this[EBO_OFFSET])
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, cubeIndices.size * 4, cubeIndices.toIntBuffer(), GLES30.GL_STATIC_DRAW)

        this[EBO_OFFSET]
    }

    private val cubeVAO: Int = with(handles) {
        GLES30.glGenVertexArrays(1, this, CUBE_VAO_OFFSET)
        this[CUBE_VAO_OFFSET]
    }

    private val lightVAO: Int = with(handles) {
        GLES30.glGenVertexArrays(1, this, LIGHT_VAO_OFFSET)
        this[LIGHT_VAO_OFFSET]
    }

    private val vertexCount: Int = 36//triangleCoords.size / COORDS_PER_VERTEX


    init {
        GLES30.glBindVertexArray(cubeVAO)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO)

        GLES30.glVertexAttribPointer(
            vPositionHandle,
            COORDS_PER_VERTEX,
            GLES30.GL_FLOAT,
            false,
            STRIDE,
            0
        )

        GLES30.glEnableVertexAttribArray(vPositionHandle)

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, normalVBO)

        GLES30.glVertexAttribPointer(
            normalVectorHandle,
            COORDS_PER_VERTEX,
            GLES30.GL_FLOAT,
            false,
            STRIDE,
            0
        )

        GLES30.glEnableVertexAttribArray(normalVectorHandle)


        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, EBO)

        GLES30.glBindVertexArray(0) //Unbind VAO

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0)

    }


    fun draw(modelMatrix: FloatArray, viewMatrix: FloatArray, projectionMatrix: FloatArray) {

        program.use()

        GLES30.glBindVertexArray(cubeVAO)

        GLES30.glUniform4fv(vColourHandle, 1, colour, 0)

        GLES30.glUniformMatrix4fv(viewHandle, 1, false, viewMatrix, 0)

        GLES30.glUniformMatrix4fv(projectionHandle, 1, false, projectionMatrix, 0)

        GLES30.glUniformMatrix4fv(modelHandle, 1, false, modelMatrix, 0)

        GLES30.glUniform3fv(6, 1, floatArrayOf(1.0f, 1.0f, 1.0f), 0)

        GLES30.glUniform3fv(7, 1, floatArrayOf(0.0f, 100.0f, 0.0f), 0)

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 36, GLES30.GL_UNSIGNED_INT, 0)

        GLES30.glBindVertexArray(0)

        //Do I still need?
        GLES30.glDisableVertexAttribArray(vPositionHandle)


    }

    companion object {
        private const val vPositionHandle: Int = 0
        private const val modelHandle: Int = 1
        private const val viewHandle: Int = 2
        private const val projectionHandle: Int = 3
        private const val normalVectorHandle: Int = 4
        private const val vColourHandle: Int = 5
        private const val VBO_OFFSET: Int = 0
        private const val NORMAL_VBO_OFFSET: Int = 1
        private const val CUBE_VAO_OFFSET: Int = 2
        private const val LIGHT_VAO_OFFSET: Int = 3
        private const val EBO_OFFSET: Int = 4


        //private const val EBO_OFFSET: Int = 2
        private const val COORDS_PER_VERTEX = 3
        private const val COORDS_PER_NORM = 3
        private const val STRIDE: Int = COORDS_PER_VERTEX * SIZEOF_FLOAT
        //private const val LIGHT_STRIDE: Int = (COORDS_PER_VERTEX + COORDS_PER_NORM) * SIZEOF_FLOAT


    }
}