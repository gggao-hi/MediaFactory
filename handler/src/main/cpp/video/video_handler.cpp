//
// Created by gggao on 12/24/2021.
//

#include "video_handler.h"
#include "video_dealer.h"


JNIEXPORT jobject JNICALL videoCommandHandler(JNIEnv *env, jobject clazz, jobject command) {

    jclass commandClazz = env->FindClass("com/ggg/handler/MediaHandler$VideoCommand");
    jobject paths = env->GetObjectField(command,
                                        env->GetFieldID(commandClazz, "paths", "Ljava/util/Map;"));
    jint type = env->GetIntField(command,
                                 env->GetFieldID(commandClazz, "type", "I"));
    auto *pHandler = new VideoHandler;
    LOGD("command:%d", type);
    switch (type) {
        case TYPE_SPLIT_SOUND:
            pHandler->splitVideo("");
            break;

        case TYPE_VIDEO_JOINT: {
            vector<string> paths;
            pHandler->jointVideo(paths);

        }
            break;
        case TYPE_ADD_INK: {
            map<string, string> paths;
            pHandler->addInk(paths);

        }
            break;
        default:
            break;

    }
    return nullptr;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    JNIEnv *env = nullptr;
    if (jvm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK || env == nullptr) {
        return -1;
    }
    jclass clazz = env->FindClass("com/ggg/handler/MediaHandler");

    JNINativeMethod methods[] = {{"sendVideoCommand",
                                         "(Lcom/ggg/handler/MediaHandler$VideoCommand;)Ljava/util/Map;",
                                         (void *) videoCommandHandler}};

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0]))) {
        return -1;
    }

    return JNI_VERSION_1_6;
}