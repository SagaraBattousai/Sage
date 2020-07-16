package com.sagarabattousai.sage.graphics.shaders

import android.opengl.GLES30
import android.util.Log
import com.sagarabattousai.sage.graphics.glGetProgramiv
import com.sagarabattousai.sage.graphics.glGetShaderiv
import com.sagarabattousai.sage.graphics.resourcers.Resourcer
import kotlin.system.exitProcess

class Shader private constructor(private val program: Int) {

    fun use(): Unit = GLES30.glUseProgram(program)


    //TODO(): Add @Inject
    class ShaderBuilder(private val resourcer: Resourcer<String>) {

        private val shaderResourceIDs: MutableSet<Pair<Int, Int>> = HashSet()

        fun withVertexShader(vertexShaderResourceID: Int): ShaderBuilder =
            apply {
                shaderResourceIDs.add(Pair(GLES30.GL_VERTEX_SHADER, vertexShaderResourceID))
            }

        fun withFragmentShader(fragmentShaderResourceID: Int): ShaderBuilder =
            apply {
                shaderResourceIDs.add(Pair(GLES30.GL_FRAGMENT_SHADER, fragmentShaderResourceID))
            }

        fun build(): Shader {
            val program: Int = GLES30.glCreateProgram()
            shaderResourceIDs.forEach { (shaderType: Int, shaderID: Int) ->
                val shader: Int = compileShader(shaderType, resourcer.getResource(shaderID))

                GLES30.glAttachShader(program, shader)
                GLES30.glLinkProgram(program)
                GLES30.glDeleteShader(shader)
            }

            val linkStatus = glGetProgramiv(program, GLES30.GL_LINK_STATUS)

            if (linkStatus == 0) {
                Log.e(LOG_TAG, GLES30.glGetProgramInfoLog(program))
                exitProcess(SHADER_COMPILE_ERROR_CODE)
            }

            return Shader(program)
        }


        private fun compileShader(type: Int, shaderCode: String): Int {

            val compiledShader = GLES30.glCreateShader(type).also { shader ->

                GLES30.glShaderSource(shader, shaderCode)
                GLES30.glCompileShader(shader)
            }

            val compileStatus: Int = glGetShaderiv(compiledShader, GLES30.GL_COMPILE_STATUS)

            Log.d(
                LOG_TAG,
                "${compileStatus}, ${GLES30.glGetString(GLES30.GL_SHADING_LANGUAGE_VERSION)}"
            )

            if (compileStatus == 0) {
                Log.e(LOG_TAG, GLES30.glGetShaderInfoLog(compiledShader))
                exitProcess(SHADER_COMPILE_ERROR_CODE)
            }

            return compiledShader
        }
    }

    companion object {

        private const val SHADER_COMPILE_ERROR_CODE = 37
        private const val LOG_TAG = "SHADER_TAG"
    }

}
