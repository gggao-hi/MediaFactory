package com.ggg.enter.model

import android.content.Intent
import com.ggg.MainApplication
import com.ggg.imagecolor.activity.ImageColorActivity
import com.ggg.mediafactory.R


/**
 * Created by  gggao on 11/26/2021.
 */
class EnterRepository {
    fun obtainEnters(): List<EnterBean> {
        val activity = MainApplication.getCurrentActivity()
        return listOf(
            EnterBean(activity?.getString(R.string.original_to_film) ?: "") {
                MainApplication.getCurrentActivity()?.apply {
                    startActivity(Intent(this, ImageColorActivity::class.java).apply {
                        putExtra("title", getString(R.string.original_to_film))
                    })
                }
            }
        )
    }
}