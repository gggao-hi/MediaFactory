package com.ggg.enter.model

import android.content.Intent
import com.ggg.base.BaseApplication
import com.ggg.handler.image.activity.ImageActivity
import com.ggg.mediafactory.R
import com.ggg.handler.video.activity.VideoActivity
import com.ggg.share_remote.ShareRemoteActivity


/**
 * Created by  gggao on 11/26/2021.
 */
class EnterRepository {
    fun obtainEnters(): List<EnterBean> {
        val activity = BaseApplication.getCurrentActivity()
        return listOf(
            EnterBean(activity?.getString(R.string.image_handler) ?: "") {
                BaseApplication.getCurrentActivity()?.apply {
                    startActivity(Intent(this, ImageActivity::class.java).apply {
                        putExtra("title", getString(R.string.image_handler))
                    })
                }
            },
            EnterBean(activity?.getString(R.string.video_handler) ?: "") {
                BaseApplication.getCurrentActivity()?.apply {
                    startActivity(Intent(this, VideoActivity::class.java).apply {
                        putExtra("title", getString(R.string.video_handler))
                    })
                }
            },
            EnterBean(activity?.getString(R.string.share_remote) ?: "") {
                BaseApplication.getCurrentActivity()?.apply {
                    startActivity(Intent(this, ShareRemoteActivity::class.java).apply {
                        putExtra("title", getString(R.string.share_remote))
                    })
                }
            }
        )
    }
}