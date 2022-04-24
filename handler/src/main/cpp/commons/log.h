//
// Created by gggao on 12/24/2021.
//
#include <android/log.h>
#include <jni.h>
#include <string>
#include <stdio.h>

using namespace std;
#ifndef MEDIAFACTORY_LOG_H
#define MEDIAFACTORY_LOG_H

#define LOGD(tag, ...) __android_log_print(ANDROID_LOG_DEBUG,tag,__VA_ARGS__)
#define LOGI(tag, ...) __android_log_print(ANDROID_LOG_INFO,tag,__VA_ARGS__)
#define LOGE(tag, ...) __android_log_print(ANDROID_LOG_ERROR,tag,__VA_ARGS__)
#endif //MEDIAFACTORY_LOG_H
