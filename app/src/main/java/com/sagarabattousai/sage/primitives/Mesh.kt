package com.sagarabattousai.sage.primitives

import android.content.res.Resources
import android.opengl.GLES30
import java.io.BufferedReader
import java.io.InputStreamReader

abstract class Mesh(private val resourcer: Resources) {


    fun loadVertexShader(shaderID: Int): Int = loadShader(GLES30.GL_VERTEX_SHADER, shaderID)

    fun loadFragmentShader(shaderID: Int): Int = loadShader(GLES30.GL_FRAGMENT_SHADER, shaderID)


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
        return GLES30.glCreateShader(type).also { shader ->

            GLES30.glShaderSource(shader, shaderCode)
            GLES30.glCompileShader(shader)
        }
    }

}