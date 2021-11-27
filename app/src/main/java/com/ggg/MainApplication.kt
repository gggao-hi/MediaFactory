package com.ggg

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MainApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        private val activities = mutableListOf<Activity>()

        fun getCurrentActivity(): Activity? {
            return activities.lastOrNull()
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        activities.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }
}