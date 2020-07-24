package com.sagarabattousai.sage.graphics.primitives

import android.opengl.Matrix

interface Mesh {

    fun draw(modelMatrix: FloatArray, viewMatrix: FloatArray, projectionMatrix: FloatArray)



}