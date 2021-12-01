//
// Created by ggg on 2021/11/27.
//
#include <android/log.h>
#include <android/bitmap.h>

#ifndef MEDIAFACTORY_HANDLER_H
#define MEDIAFACTORY_HANDLER_H
#define TAG "handler"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#endif //MEDIAFACTORY_HANDLER_H
