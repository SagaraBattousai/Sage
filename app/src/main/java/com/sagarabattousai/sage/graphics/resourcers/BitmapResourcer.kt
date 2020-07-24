package com.sagarabattousai.sage.graphics.resourcers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class BitmapResourcer(private val res: Resources) : Resourcer<Bitmap> {


    override fun getResource(resourceID: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inScaled = false // No pre-scaling

        return BitmapFactory.decodeResource(res, resourceID, options)

    }


}