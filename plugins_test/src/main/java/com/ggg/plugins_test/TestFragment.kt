package com.ggg.plugins_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ggg.base.plugin_interface.IPluginObserver

class TestFragment(test:String) : Fragment(), IPluginObserver {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun getFragment(): Fragment {
        return this
    }
}

