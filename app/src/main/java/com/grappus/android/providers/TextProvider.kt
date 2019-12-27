package com.grappus.android.providers

import android.content.res.Resources
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - A Wrapper class to simplify passing a normal [String], [StringRes], or a [PluralsRes]
 * to a method or a calls constructor without having to create overload methods.
 */

sealed class TextProvider {

    /**
     * Used to pass a normal string text.
     */
    class Str(val text: String) : TextProvider()

    /**
     * Used to pass a spannable string text.
     */
    class Styled(val text: CharSequence) : TextProvider()

    /**
     * Used to pass a [StringRes] text.
     */
    class Res(@StringRes val resId: Int, vararg val args: Any) : TextProvider()

    /**
     * Used to pass a [PluralsRes] text.
     */
    class Plural(@PluralsRes val resId: Int, val quantity: Int, vararg val args: Any) :
        TextProvider()

    /**
     * Uses the provided [format] as a format string and returns a string obtained by substituting
     * the specified [Text] arguments.
     */
    class Format(val format: String, vararg val args: TextProvider) : TextProvider()

    /**
     * Used to pass an empty [Text] value.
     */
    object Empty : TextProvider()

    fun getText(resources: Resources): String {
        return getStyledText(resources).toString()
    }

    fun getStyledText(resources: Resources): CharSequence {
        return when (this) {
            is Str -> text
            is Styled -> text
            is Res -> resources.getString(resId, *args)
            is Plural -> resources.getQuantityString(resId, quantity, *args)
            is Format -> {
                val values = args.map { it.getText(resources) }.toTypedArray()
                String.format(format, *values)
            }
            is Empty -> ""
        }
    }
}