
#include "image_handler.h"

/**
 * 图片像素数组为一维数组
 * 像素数组中 index的规则为 (width*(height 的index)+(width的index)
 */

extern "C"
JNIEXPORT jint JNICALL
Java_com_ggg_handler_MediaHandler_negative(JNIEnv *env, jobject thiz, jobject bitmap) {
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
    auto *srcPix = (uint32_t *) bitmapPixels;
    // 在C层中，Bitmap像素点的值是ABGR，而不是ARGB，也就是说，高端到低端：A，B，G，R

    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            uint32_t color = srcPix[w * i + j];
            uint32_t blue = (color >> 16) & 0xFF;
            uint32_t green = (color >> 8) & 0xFF;
            uint32_t red = color & 0xFF;
            uint32_t alpha = (color >> 24) & 0xFF;
            // 底片效果算法原理：将当前像素点的RGB值分别与255之差后的值作为当前点的RGB值，即
            // R = 255 – R；G = 255 – G；B = 255 – B
            red = 255 - red;
            green = 255 - green;
            blue = 255 - blue;
            uint32_t newColor = (alpha << 24) | (blue << 16) | (green << 8) | red;
            srcPix[w * i + j] = newColor;

        }
    }
    AndroidBitmap_unlockPixels(env, bitmap);

    return SUCCESS;
}

void vertical(uint32_t *pixels, int w, int h) {
    for (int i = 0; i < h / 2; ++i) {
        for (int j = 0; j < w; ++j) {
            uint32_t temp = pixels[w * i + j];
            pixels[w * i + j] = pixels[w * (h - i) + j];
            pixels[w * (h - i) + j] = temp;
        }
    }
}

void horizontal(uint32_t *pixels, int w, int h) {
    for (int i = 0; i < h; ++i) {
        for (int j = 0; j < w / 2; ++j) {
            uint32_t temp = pixels[w * i + j];
            pixels[w * i + j] = pixels[w * i + w - j];
            pixels[w * i + w - j] = temp;
        }
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_ggg_handler_MediaHandler_flip(JNIEnv *env, jobject thiz, jobject bitmap, jint direction) {
    AndroidBitmapInfo bitmapInfo;
    int infoState = AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);
    if (infoState != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOGE("AndroidBitmap_getInfo error:%d", infoState);
        return FAILED;
    }

    void *pixels;
    int lockState = AndroidBitmap_lockPixels(env, bitmap, &pixels);
    if (lockState != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOGE("AndroidBitmap_lockPixels error:%d", lockState);
        return FAILED;
    }
    int w = bitmapInfo.width;
    int h = bitmapInfo.height;
    if (direction == VERTICAL) {
        vertical((uint32_t *) pixels, w, h);
    } else {
        horizontal((uint32_t *) pixels, w, h);
    }
    AndroidBitmap_unlockPixels(env, bitmap);
    return SUCCESS;
}
