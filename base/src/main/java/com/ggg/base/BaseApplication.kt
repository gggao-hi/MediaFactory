package com.ggg.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

open class BaseApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        private val activities = mutableListOf<Activity>()

        fun getCurrentActivity(): Activity? {
            return activities.lastOrNull()
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("ExceptionHandler", "thread:${t.id}")
            e.printStackTrace()
            activities.forEach {
                it.finish()
            }
            android.os.Process.killProcess(android.os.Process.myPid())
        }

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