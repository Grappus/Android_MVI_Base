package com.grappus.android.binding

import android.view.View
import androidx.databinding.BindingConversion

/**
 * Created by cp@grappus.com on 20/12/2019.
 * Description - Binding converters to enable custom data types to be mapped with native params
 */

object BindingConverters {

    //Converter to change View's boolean visibility implementation to VISIBLE & GONE
    @JvmStatic
    @BindingConversion
    fun setVisibility(isVisible: Boolean): Int {
        return if (isVisible) View.VISIBLE else View.GONE
    }
}