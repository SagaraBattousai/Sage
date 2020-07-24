package com.sagarabattousai.sage.graphics

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class SageGLView(context: Context) : GLSurfaceView(context) {

    private val renderer: SageGLRenderer

    init {
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)

        setEGLContextClientVersion(3)

        renderer = SageGLRenderer()

        setRenderer(renderer)

    }

    companion object {
        private const val LOG_TAG = "SAGE_GL_VIEW"
        private const val DEBUG = true
    }

    private class SageGLRenderer : Renderer {

        override fun onDrawFrame(unused: GL10?) {
            SageJNIGraphics.step()
        }

        override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
            SageJNIGraphics.resize(width, height)
        }

        override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
            SageJNIGraphics.init()
        }
    }




}
