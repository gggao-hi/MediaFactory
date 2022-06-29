package com.ggg

import com.ggg.base.BaseApplication
import com.ggg.plugins_loader.PluginsLoader

class MainApplication : BaseApplication(){
    override fun onCreate() {
        super.onCreate()
        PluginsLoader.init(this)
    }
}