package com.sagarabattousai.sage.graphics.resourcers

import android.content.res.Resources
import java.io.BufferedReader
import java.io.InputStreamReader

//TODO(): Add @Injector
class ShaderResourcer(private val res: Resources) : Resourcer<String> {

    override fun getResource(shaderID: Int): String {
        val inStream =
            BufferedReader(
                InputStreamReader(
                    res.openRawResource(shaderID)
                )
            )

        var shader = ""

        while (true) {
            val line: String = inStream.readLine() ?: break
            shader += "$line\n"
        }

        inStream.close()

        return shader
    }

}