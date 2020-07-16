package com.sagarabattousai.sage.graphics

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import androidx.core.os.persistableBundleOf
import com.sagarabattousai.sage.graphics.primitives.Cube
import com.sagarabattousai.sage.graphics.primitives.Triangle
import com.sagarabattousai.sage.graphics.resourcers.BitmapResourcer
import com.sagarabattousai.sage.graphics.resourcers.ShaderResourcer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


//24 x 18 =
private const val MODEL_SCALE_X = 0.25f//1.0f / 24.0f
private const val MODEL_SCALE_Y = 0.25f//1.0f / 24.0f
private const val MODEL_TRANSLATION_Z = 0.0f//-5.0f

class SageGLRenderer(private val context: Context): GLSurfaceView.Renderer {

    //@Volatile
    //var angle: Float = 0.0f

    private val modelMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
        //Matrix.scaleM(this, 0, MODEL_SCALE_X, MODEL_SCALE_Y, 1.0f)
        //Matrix.rotateM(this, 0, 17.0f, 0.0f, 1.0f, 1.0f)
        //Matrix.translateM(this, 0, 0.0f, 0.0f, MODEL_TRANSLATION_Z)
    }

    private val viewMatrix = FloatArray(16)
    init {
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 6f, 0f, 0f, 2f, 0f, 1f, 0f)
    }

    private val projectionMatrix = FloatArray(16)

    private lateinit var cube: Cube

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES30.glClearColor(0.0f, 0.25f, 0.37f, 1.0f)

        cube = Cube(ShaderResourcer(context.resources), BitmapResourcer(context.resources))

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()

        Log.d(LOG_TAG, "width: $width, height: $height, ratio: $ratio")

        Matrix.perspectiveM(projectionMatrix, 0, 45.0f, ratio, 0.1f, 100.0f)

    }

    var tmp =10.0f

    override fun onDrawFrame(gl: GL10?) {

        //val scratch = FloatArray(16)

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        Matrix.setRotateM(modelMatrix, 0, tmp, 1f, 0f, 0.0f)
        Matrix.translateM(modelMatrix, 0, 0.0f, 0.0f, MODEL_TRANSLATION_Z)

        tmp = (tmp + 10) % 360

        cube.draw(modelMatrix, viewMatrix, projectionMatrix)
    }

    companion object {
        const val LOG_TAG = "RendererLog"
    }
}