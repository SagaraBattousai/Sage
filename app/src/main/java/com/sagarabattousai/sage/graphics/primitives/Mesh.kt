package com.sagarabattousai.sage.graphics.primitives

import android.content.res.Resources
import android.opengl.GLES30
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.system.exitProcess

private const val SHADER_COMPILE_ERROR_CODE = 37

abstract class Mesh(private val resourcer: Resources) {


    fun compileVertexShader(shaderID: Int): Int = loadShader(GLES30.GL_VERTEX_SHADER, shaderID)

    fun compileFragmentShader(shaderID: Int): Int = loadShader(GLES30.GL_FRAGMENT_SHADER, shaderID)

    fun linkShaders(vararg shaders: Int): Int =
        GLES30.glCreateProgram().also {
            shaders.forEach { shader -> GLES30.glAttachShader(it, shader) }
            GLES30.glLinkProgram(it)
        }


    private fun loadShader(type: Int, shaderID: Int): Int {
        val shaderCode: String = readShader(shaderID)
        return compileShader(type, shaderCode)
    }

    //May need to un-privatise
    private fun readShader(shaderID: Int): String {
        val inStream = BufferedReader(InputStreamReader(resourcer.openRawResource(shaderID)))

        var shader: String = ""

        while (true) {
            val line: String = inStream.readLine() ?: break
            shader += "$line\n"
        }

        inStream.close()

        return shader
    }

    //May need to un-privatise
    private fun compileShader(type: Int, shaderCode: String): Int {

        val compiledShader = GLES30.glCreateShader(type).also { shader ->

            GLES30.glShaderSource(shader, shaderCode)
            GLES30.glCompileShader(shader)
        }

        val compileStatus = IntArray(1)

        GLES30.glGetShaderiv(compiledShader, GLES30.GL_COMPILE_STATUS, compileStatus, 0)

        Log.d(
            LOG_TAG,
            "${compileStatus[0]}, ${GLES30.glGetString(GLES30.GL_SHADING_LANGUAGE_VERSION)}"
        )

        if (compileStatus[0] == 0) {
            Log.e(LOG_TAG, GLES30.glGetShaderInfoLog(compiledShader))
            exitProcess(SHADER_COMPILE_ERROR_CODE)
        }


        return compiledShader
    }


    companion object {
        private const val LOG_TAG = "MESH_LOG_TAG"
    }

}