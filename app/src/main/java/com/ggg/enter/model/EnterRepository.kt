package com.ggg.enter.model

import android.content.Intent
import com.ggg.MainApplication
import com.ggg.imagecolor.activity.ImageColorActivity


/**
 * Created by  gggao on 11/26/2021.
 */
class EnterRepository {
    fun obtainEnters(): List<EnterBean> = listOf(
        EnterBean("图片色度调节") {
            MainApplication.getCurrentActivity()?.apply {
                startActivity(Intent(this, ImageColorActivity::class.java).apply {
                    putExtra("title", "图片色度调节")
                })
            }
        }
    )
}