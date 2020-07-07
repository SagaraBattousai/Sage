package com.sagarabattousai.sage.graphics

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.sagarabattousai.sage.graphics.primitives.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SageGLRenderer(private val context: Context): GLSurfaceView.Renderer {

    @Volatile
    var angle: Float = 0.0f

    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    private val rotationMatrix = FloatArray(16)

    private lateinit var triangle: Triangle

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        triangle = Triangle(context.resources)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0,0, width, height)

        val ratio: Float =  width.toFloat() / height.toFloat()

        Log.d(LOG_TAG, "width: $width, height: $height, ratio: $ratio")

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)

    }

    override fun onDrawFrame(gl: GL10?) {

        val scratch = FloatArray(16)


        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        Matrix.setLookAtM(viewMatrix, 0, 0f,0f,-3f, 0f,0f,0f,0f,1f,0f)

        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        //val time = SystemClock.uptimeMillis() % 4000L

        //val angle = 0.090f * time.toInt()

        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)

        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

        triangle.draw(scratch)
    }

}

const val LOG_TAG = "RendererLog"
