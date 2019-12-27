package com.grappus.android.logic.extensions

/**
 * Created by nimish@grappus.com on 16/12/2019.
 * Description - Common data type extensions
 */

fun Int?.toFormattedString(): String {
    return when {
        this == null -> ""
        else -> this.toString()
    }
}

fun Int?.orZero(): Int {
    return when {
        this == null -> 0
        else -> this
    }
}

fun Float?.toFormattedString(): String {
    return when {
        this == null -> ""
        this.toInt().toFloat() == this -> this.toInt().toString()
        else -> this.toString()
    }
}

fun Float?.orZero(): Float {
    return when {
        this == null -> 0.0f
        else -> this
    }
}

fun Long?.orZero(): Long {
    return when {
        this == null -> 0
        else -> this
    }
}