//
// Created by gggao on 12/24/2021.
//

#include "video_handler.h"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    JNIEnv *env = nullptr;
    if (jvm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK||env== nullptr) {
        return -1;
    }


    return JNI_VERSION_1_6;
}