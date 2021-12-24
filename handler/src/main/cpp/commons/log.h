//
// Created by gggao on 12/24/2021.
//
#include <android/log.h>
#include <jni.h>
#include <string>

#ifndef MEDIAFACTORY_LOG_H
#define MEDIAFACTORY_LOG_H
#define TAG "handler"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)
#endif //MEDIAFACTORY_LOG_H
