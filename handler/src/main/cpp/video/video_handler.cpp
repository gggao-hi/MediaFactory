//
// Created by gggao on 12/24/2021.
//

#include "video_handler.h"
#include "video_dealer.h"
#include <thread>

jobject gl_command;
jclass gl_command_clazz;
jclass gl_resultListener_clazz;
jobject gl_result_listener;
jobject gl_surface;
JavaVM *current_jvm;

bool getEnv(JNIEnv **env) {
    JavaVMAttachArgs args;
    args.version = JNI_VERSION_1_6;
    args.name = "thread-handler_video";
    args.group = nullptr;
    int status = current_jvm->GetEnv((void **) env, JNI_VERSION_1_6);
    LOGD("thread:", "GetEnv status:%d\n", status);
    if (status != JNI_OK) {
        status = current_jvm->AttachCurrentThread(env, &args);
        LOGD("thread:", "AttachCurrentThread status:%d\n", status);
    }
    if (status != JNI_OK) {
        return false;
    }
    return true;
}

void releaseEnv() {
    JNIEnv *env;
    if (current_jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_OK) {
        env->DeleteGlobalRef(gl_command);
        env->DeleteGlobalRef(gl_command_clazz);
        env->DeleteGlobalRef(gl_resultListener_clazz);
        env->DeleteGlobalRef(gl_result_listener);
        if (gl_surface != nullptr) {
            env->DeleteGlobalRef(gl_surface);
        }
        current_jvm->DetachCurrentThread();
    }
}

/**
 * 在子线程中env->FindClass 总是返回null,
 * 原因是 AttachCurrentThread 只会加载 java 系统中的class 不会加载自定义的class
 * **/
void run() {
    JNIEnv *env = nullptr;
    if (getEnv(&env)) {

        jobject paths = env->GetObjectField(gl_command,
                                            env->GetFieldID(gl_command_clazz, "paths",
                                                            "Ljava/util/Map;"));
        jint type = env->GetIntField(gl_command,
                                     env->GetFieldID(gl_command_clazz, "type", "I"));

        auto *pHandler = new VideoHandler(env);
        LOGD("videoCommandHandler", "command:%d", type);
        int result_code = -1;
        switch (type) {
            case TYPE_SPLIT_SOUND:
                result_code = pHandler->splitVideo(paths);
                break;
            case TYPE_VIDEO_JOINT:
                result_code = pHandler->jointVideo(paths);
                break;
            case TYPE_ADD_INK:
                result_code = pHandler->addInk(paths);
                break;
            case TYPE_DECODER:
                result_code = pHandler->decode(paths, gl_surface);
                break;
            default:
                break;

        }

        env->CallVoidMethod(gl_result_listener,
                            env->GetMethodID(gl_resultListener_clazz, "onResult", "(I)V"),
                            result_code);
    }
    releaseEnv();
}

JNIEXPORT void JNICALL
videoCommandHandler(JNIEnv *env, jobject clazz, jobject command, jobject surface,
                    jobject result_listener) {

    jclass commandClazz = env->FindClass("com/ggg/handler/MediaHandler$VideoCommand");
    jclass resultListenerClazz = env->FindClass(
            "com/ggg/handler/MediaHandler$OnHandlerResultListener");
    if (surface != nullptr) {
        gl_surface = env->NewGlobalRef(surface);
    }
    gl_command = env->NewGlobalRef(command);
    gl_result_listener = env->NewGlobalRef(result_listener);
    gl_command_clazz = static_cast<jclass>(env->NewGlobalRef(commandClazz));
    gl_resultListener_clazz = static_cast<jclass>(env->NewGlobalRef(resultListenerClazz));
    std::thread t(run);
    t.join();
}

JNIEXPORT void JNICALL
initHandler(JNIEnv *env, jobject clazz, jobject surface){

}
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    JNIEnv *env = nullptr;
    current_jvm = jvm;
    if (jvm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK || env == nullptr) {
        return -1;
    }
    jclass clazz = env->FindClass("com/ggg/handler/MediaHandler");

    JNINativeMethod methods[] = {{"sendVideoCommand",
                                         "(Lcom/ggg/handler/MediaHandler$VideoCommand;Landroid/view/Surface;Lcom/ggg/handler/MediaHandler$OnHandlerResultListener;)V",
                                         (void *) videoCommandHandler},
                                 {"initVideoHandler", "(Landroid/view/Surface;)V",(void *)initHandler}};

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0]))) {
        return -1;
    }

    return JNI_VERSION_1_6;
}

