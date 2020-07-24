package com.sagarabattousai.sage.graphics

class SageJNIGraphics {

    companion object {

        init {
            System.loadLibrary("sageGraphicsJNI")
        }

        @JvmStatic external fun init()

        @JvmStatic external fun resize(width: Int, height: Int)

        @JvmStatic external fun step()
    }
}