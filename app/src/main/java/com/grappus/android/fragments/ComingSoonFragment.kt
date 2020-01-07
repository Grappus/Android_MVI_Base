package com.grappus.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grappus.android.R

/**
 * Created by cp@grappus.com on 24/12/2019.
 * Description - Dummy fragment for features planned in future releases
 */

class ComingSoonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false)
    }
}