package com.sagarabattousai.sage.graphics

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.view.MotionEvent

private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f

class SageGLView(context: Context) : GLSurfaceView(context) {

    private val renderer: SageGLRenderer

    init {
        setEGLContextClientVersion(3)

        renderer = SageGLRenderer(context)

        setRenderer(renderer)

        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        GLES30.glEnable(GLES30.GL_DEPTH_TEST)

        GLES30.glDepthFunc(GLES30.GL_LESS)

    }

    private var previousX: Float = 0.0f
    private var previousY: Float = 0.0f


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x: Float = event.x
        val y: Float = event.y

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {

                var dx: Float = x - previousX
                var dy: Float = y - previousY

                if (y > height / 2) {
                    dx *= -1
                }

                if (x < width / 2) {
                    dy *= -1
                }

                renderer.angle += (dy + dx) * TOUCH_SCALE_FACTOR
                requestRender()
            }
        }

        previousX = x
        previousY = y
        return true

    }

}
