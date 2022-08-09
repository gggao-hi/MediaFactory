package com.ggg.handler.video.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraManager.AvailabilityCallback
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.Surface
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.ggg.base.BaseApplication
import com.ggg.handler.R


/**
 * Created by  gggao on 1/14/2022.
 */
class RecordViewModel : ViewModel() {
    private val handlerThread: HandlerThread = HandlerThread("record")
    private var handler: Handler? = null
    private var cameraManager: CameraManager? = null
    private var cameraDevice: CameraDevice? = null
    private var surface: Surface? = null
    private var imageReader: ImageReader? = null
    private val callback = object : AvailabilityCallback() {}
    private var cameraSession: CameraCaptureSession? = null
    private var videoWidth: Int = 0
    private var viewHeight: Int = 0
    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                BaseApplication.getCurrentActivity()!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                BaseApplication.getCurrentActivity()!!,
                R.string.camera_permission_denied,
                Toast.LENGTH_LONG
            ).show()
            return
        }
        cameraManager?.apply {
            openCamera(cameraIdList[0], object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    surface?.let {
                        cameraDevice = camera
                        camera.createCaptureSession(
                            arrayListOf(surface),
                            object : CameraCaptureSession.StateCallback() {
                                override fun onConfigured(session: CameraCaptureSession) {
                                    cameraSession = session
                                    session.setRepeatingRequest(camera.createCaptureRequest(
                                        CameraDevice.TEMPLATE_PREVIEW
                                    )
                                        .apply {
                                            addTarget(it)
                                            setCameraParams(this)
                                        }
                                        .build(),
                                        object : CameraCaptureSession.CaptureCallback() {},
                                        handler
                                    )
                                }

                                override fun onConfigureFailed(session: CameraCaptureSession) {
                                }
                            },
                            handler
                        )
                    }

                }

                override fun onDisconnected(camera: CameraDevice) {
                }

                override fun onError(camera: CameraDevice, error: Int) {
                }

            }, handler)
        }

    }

    private fun setCameraParams(builder: CaptureRequest.Builder) {
        builder.set(
            CaptureRequest.CONTROL_AF_MODE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
        )
    }

    fun record() {
        this.imageReader =
            ImageReader.newInstance(videoWidth, viewHeight, ImageFormat.YUV_420_888, 20).apply {
                setOnImageAvailableListener({

                    it?.acquireNextImage()?.apply {
                        Log.d("xxx", "image data:${this.planes}")
                    }
                }, handler)
            }
        cameraDevice?.let { camera ->
            camera.createCaptureSession(
                arrayListOf(surface, imageReader!!.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        cameraSession = session
                        session.setRepeatingRequest(camera.createCaptureRequest(
                            CameraDevice.TEMPLATE_RECORD
                        )
                            .apply {
                                addTarget(surface!!)
                                addTarget(imageReader!!.surface)
                                setCameraParams(this)
                            }
                            .build(),
                            object : CameraCaptureSession.CaptureCallback() {},
                            handler
                        )
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                    }
                },
                handler
            )

        }

    }

    fun stopRecord() {
        Log.d("xxx","stopRecord")
        imageReader?.close()
        imageReader = null
        cameraDevice?.let { camera ->
            camera.createCaptureSession(
                arrayListOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        cameraSession = session
                        session.setRepeatingRequest(camera.createCaptureRequest(
                            CameraDevice.TEMPLATE_PREVIEW
                        )
                            .apply {
                                addTarget(surface!!)
                                setCameraParams(this)
                            }
                            .build(),
                            object : CameraCaptureSession.CaptureCallback() {},
                            handler
                        )
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                    }
                },
                handler
            )
        }
    }

    private fun initCamera() {
        Log.d("xxx", "initCamera")
        BaseApplication.getCurrentActivity()?.apply {
            Log.d("xxx", "BaseApplication.getCurrentActivity()")
            cameraManager =
                this.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            cameraManager?.registerAvailabilityCallback(
                callback, handler
            )
            openCamera()
        }
    }


    fun closeCamera() {
        handlerThread.quitSafely()
        cameraManager?.unregisterAvailabilityCallback(callback)
        imageReader?.close()
        cameraSession?.close()

    }

    fun loadVideo(surface: Surface?, videoWidth: Int, viewHeight: Int) {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        this.surface = surface
        this.viewHeight = viewHeight
        this.videoWidth = videoWidth
        surface?.apply { initCamera() }

    }
}