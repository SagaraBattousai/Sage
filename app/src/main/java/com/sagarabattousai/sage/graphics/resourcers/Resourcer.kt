package com.sagarabattousai.sage.graphics.resourcers

interface Resourcer<out T> {

    fun getResource(resourceID: Int): T

}