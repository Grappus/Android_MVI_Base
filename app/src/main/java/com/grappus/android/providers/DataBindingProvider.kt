package com.grappus.android.providers

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.grappus.android.extensions.getLayoutInflater
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by cp@grappus.com on 19/12/2019.
 * Description - Custom data binding util
 */

/**
 * This is a replacement for DataBindingUtil.setContentView(activity, layoutRes)
 * use val binding: ActivityMainBinding by BindActivity(R.layout.activity_main) instead
 */
class BindActivity<in R : Activity, out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null) {
            value = DataBindingUtil.setContentView<T>(thisRef, layoutRes)
        }
        return value!!
    }
}

/**
 * This is a replacement for DataBindingUtil.inflate(inflater, layoutRes, rootView, boolean)
 * use private val binding: FragmentMainBinding by BindFragment(R.layout.fragment_main) instead
 */
class BindFragment<in R : Fragment, out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null) {
            value = DataBindingUtil.inflate<T>(
                thisRef.layoutInflater, layoutRes,
                thisRef.view?.rootView as ViewGroup?, false
            )
        }
        return value!!
    }
}

/**
 * This is a replacement for DataBindingUtil.inflate(inflater, layoutRes, rootView, boolean)
 * use private val binding: ViewGroupBinding by BindViewGroup(R.layout.fragment_main) instead
 */
class BindViewGroup<in R : ViewGroup, out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null) {
            value = DataBindingUtil.inflate<T>(
                thisRef.getLayoutInflater(), layoutRes,
                thisRef, true
            )
        }
        return value!!
    }
}

/**
 * This is a replacement for DataBindingUtil.bind
 * val binding: ItemPlanetBinding by BindView(view)
 */
class BindListItem<in R : RecyclerView.ViewHolder, out T : ViewDataBinding>(
    private val view: View
) : ReadOnlyProperty<R, T> {

    private var value: T? = null

    override operator fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == null) {
            value = DataBindingUtil.bind<T>(view)
        }
        return value!!
    }
}