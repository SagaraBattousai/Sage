package com.sagarabattousai.sage

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sagarabattousai.sage.graphics.SageGLView

class MainActivity : AppCompatActivity() {

    private lateinit var glView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        glView = SageGLView(this)

        setContentView(glView)
    }
}