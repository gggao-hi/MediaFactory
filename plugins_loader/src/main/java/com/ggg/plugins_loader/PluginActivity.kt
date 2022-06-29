package com.ggg.plugins_loader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ggg.plugins_loader.plugins.TestPluginLoader


/**
 * Created by  gggao on 7/1/2022.
 */
class PluginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestPluginLoader.loadTestFragment()?.also {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it)
                .commitAllowingStateLoss()
        }
    }
}