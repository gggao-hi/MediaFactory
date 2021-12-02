#include <jni.h>
#include <string>
#include "handler.h"
#include <thread>

//https://www.jianshu.com/p/8395ddf3df54
extern "C"
JNIEXPORT jint JNICALL
Java_com_ggg_handler_NativeLib_negative(JNIEnv *env, jobject thiz, jobject bitmap) {
    AndroidBitmapInfo bitmapInfo;
    int infoRet = AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);
    if (infoRet != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOGE("AndroidBitmap_getInfo error: %d", infoRet);
        return FAILED;
    }
    void *bitmapPixels;
    int pixRet = AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels);
    if (pixRet != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOGE("AndroidBitmap_lockPixels error: %d", pixRet);
        return FAILED;
    }
    int w = bitmapInfo.width;
    int h = bitmapInfo.height;

    return SUCCESS;
}