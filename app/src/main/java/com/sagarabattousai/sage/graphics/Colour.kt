package com.sagarabattousai.sage.graphics

import java.lang.NumberFormatException
import kotlin.reflect.KProperty

private const val HEX_RADIX = 16
private const val FLOAT_SCALE = 255.0f

class Colour(private val mRed: Int, private val mGreen: Int, private val mBlue: Int, private val mAlpha: Int) {

    var red:   Int by ColourDelegate(
        mRed
    )
    var green: Int by ColourDelegate(
        mGreen
    )
    var blue:  Int by ColourDelegate(
        mBlue
    )
    var alpha: Int by ColourDelegate(
        mAlpha
    )

    //Copy Constructor
    constructor(other: Colour) : this(
        mRed = other.mRed,
        mGreen = other.mGreen,
        mBlue = other.mBlue,
        mAlpha = other.mAlpha
    )

    constructor(colour: Int) :
            this(
                ((colour and 0xFF000000.toInt()) ushr 24),
                ((colour and 0x00FF0000.toInt()) ushr 16),
                ((colour and 0x0000FF00.toInt()) ushr 8),
                ((colour and 0x000000FF.toInt()))
            )

    constructor(colour: String) : this(colour.toColour() ?: Colour(
        0,
        0,
        0,
        1
    )
    )

    fun toFloatArray(): FloatArray {
        val fRed:   Float = red.toFloat()   / FLOAT_SCALE
        val fGreen: Float = green.toFloat() / FLOAT_SCALE
        val fBlue:  Float = blue.toFloat()  / FLOAT_SCALE
        val fAlpha: Float = alpha.toFloat() / FLOAT_SCALE

        return floatArrayOf(fRed, fGreen, fBlue, fAlpha)
    }





    private class ColourDelegate(private var colour: Int) {
        operator fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return colour
        }

        operator fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            colour = value.coerceIn(0, 100)
        }
    }

    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 255
    }


}

fun Int.toColour(): Colour =
    Colour(this)

fun String.toColour(): Colour? {
    if (!this.startsWith("#")) return null

    val colourList: List<Int> =
        this.trimMargin("#")
            .chunked(2)
            .map {
                try {
                    it.toInt(HEX_RADIX)
                } catch (e: NumberFormatException) {
                    0
                }
            }

    return Colour(
        colourList[0],
        colourList[1],
        colourList[2],
        colourList[3]
    )

}