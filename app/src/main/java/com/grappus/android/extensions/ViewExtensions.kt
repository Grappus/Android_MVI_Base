package com.grappus.android.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.snackbar.Snackbar
import com.grappus.android.basemvi.R
import com.grappus.android.providers.TextProvider

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - Extension functions for Fragments
 */

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater() = context.getLayoutInflater()

inline fun View.showErrorSnack(
    message: TextProvider,
    length: Int = Snackbar.LENGTH_LONG,
    action: Snackbar.() -> Unit = {}
) = showSnack(
    message = message,
    length = length,
    action = action,
    backgroundColor = context.getColorCompat(R.color.colorTextError),
    textColor = context.getColorCompat(R.color.colorText),
    actionTextColor = context.getColorCompat(R.color.colorTextAction)
)

inline fun View.showSnack(
    message: TextProvider,
    length: Int = Snackbar.LENGTH_LONG,
    @ColorInt textColor: Int? = context.getColorCompat(R.color.colorText),
    @ColorInt backgroundColor: Int? = context.getColorCompat(R.color.colorTextError),
    @ColorInt actionTextColor: Int? = null,
    @Px verticalPadding: Int? = null,
    action: Snackbar.() -> Unit = {}
): Snackbar {
    val snackBar = Snackbar.make(this, message.getStyledText(resources), length)
    val view = snackBar.view
    val tvMessage: TextView = view.findViewById(R.id.snackbar_text)
    val tvAction: TextView = view.findViewById(R.id.snackbar_action)

    textColor?.let { tvMessage.setTextColor(it) }

    backgroundColor?.let { view.setBackgroundColor(it) }

    actionTextColor?.let { tvAction.setTextColor(actionTextColor) }

    tvMessage.apply {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            verticalPadding?.let { updateMargins(top = it, bottom = it) }
        }
        maxLines = Int.MAX_VALUE
    }

    snackBar.action()
    snackBar.show()
    return snackBar
}

fun EditText.doAfterTextChange(action: (s: Editable?, listener: TextWatcher) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            action.invoke(s, this)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}