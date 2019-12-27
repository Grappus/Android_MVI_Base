package com.grappus.android.binding

import android.os.Build
import android.text.Html
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Custom binding adapter
 */

@Singleton
class AppBindingAdapters @Inject constructor() {

    /**
     * Bind an action to an EditText when the "done" key is pressed.
     *
     * @receiver editText The EditText to listen to.
     * @param action The action to trigger when the "done" key is pressed.
     */
    @BindingAdapter("onKeyDone")
    fun EditText.onKeyDone(action: Runnable) {
        val editText = this
        editText.setOnKeyListener { _, keyCode, event ->
            var handled = false
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                action.run()
                handled = true
            }
            handled
        }
    }

    /**
     * Bind html text to an TextView.
     *
     * @receiver TextView
     * @param htmlText The HTML text to set on TextView.
     */
    @BindingAdapter("textHtml")
    fun TextView.setHtmlText(htmlText: String) {
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlText)
        }
    }
}