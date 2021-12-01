#include <jni.h>
#include <string>
#include "handler.h"
#include <thread>
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_ggg_handler_NativeLib_changeImageGray(JNIEnv *env, jobject thiz, jintArray buff, jint width,
                                               jint height) {
    LOGD("Gray = R*0.299 + G*0.587 + B*0.114, 128>>7 =1");
    jint *buf_ptr = (*env).GetIntArrayElements(buff, nullptr);
    int alpha = 0xFF;
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int color = buf_ptr[width * i + j];
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            color = (red * 38 + green * 75 + blue * 15) >> 7;
            color = (alpha << 24) | (color << 16) | (color << 8) | color;
            buf_ptr[width * i + j] = color;
        }
    }
    int size = width * height;
    jintArray result = (*env).NewIntArray(size);
    (*env).SetIntArrayRegion(result, 0, size, buf_ptr);
    return result;

}