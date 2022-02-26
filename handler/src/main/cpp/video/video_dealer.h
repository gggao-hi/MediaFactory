//
// Created by Administrator on 2022/1/8.
//
extern "C" {

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libavutil/avutil.h>
#include <libavutil/imgutils.h>
}

#include <string>
#include <map>
#include <stdio.h>
#include <time.h>
#include "../commons/log.h"

using namespace std;

class VideoHandler {
private:
    JNIEnv *env;
    AVFormatContext *pFormatCtx;
    AVCodecContext *pCodecCtx;
    const AVCodec *pCodec;
    AVFrame *pFrame, *pFrameYUV;
    AVPacket *packet;
    SwsContext *imgConvertCtx;
    FILE *fpYUV;

    static void ffmpegLog(void *ptr, int level, const char *fmt, va_list vl) {
        FILE *fp = fopen("/storage/emulated/0/av_log.txt", "a+");
        if (fp) {
            vfprintf(fp, fmt, vl);
            fflush(fp);
            fclose(fp);
        }
    }

    void init() {
        av_log_set_callback(ffmpegLog);
        avformat_network_init();

        pFormatCtx = avformat_alloc_context();

    }

    void unInit() {
        avformat_network_deinit();
    }

    map<string, string> *parseParams(jobject param) {
        jclass mapClazz = env->FindClass("java/util/HashMap");
        jmethodID keyMethodID = env->GetMethodID(mapClazz, "keySet", "()Ljava/util/Set;");
        jmethodID getMethodID = env->GetMethodID(mapClazz, "get",
                                                 "(Ljava/lang/Object;)Ljava/lang/Object;");
        jobject setKey = env->CallObjectMethod(param, keyMethodID);
        jclass setClazz = env->FindClass("java/util/Set");
        jmethodID toArrayMethodID = env->GetMethodID(setClazz, "toArray", "()[Ljava/lang/Object;");
        jobjectArray objArray = (jobjectArray) env->CallObjectMethod(setKey, toArrayMethodID);
        if (objArray == nullptr) {
            return nullptr;
        }
        jsize arraySize = env->GetArrayLength(objArray);
        map<string, string> *result = new map<string, string>;
        for (int i = 0; i < arraySize; ++i) {

            jstring key = (jstring) env->GetObjectArrayElement(objArray, i);
            jstring value = (jstring) env->CallObjectMethod(param, getMethodID, key);
            const char *keyStr = env->GetStringUTFChars(key, nullptr);
            const char *valueStr = env->GetStringUTFChars(value, nullptr);
            result->insert(pair<string, string>(keyStr, valueStr));
        }

        return result;

    }

public:
    VideoHandler(JNIEnv *env) : env(env) {
        init();
    }

    ~VideoHandler() {
        unInit();
    }

    int decode(jobject params);

    int splitVideo(jobject params);

    int jointVideo(jobject params);

    int addInk(jobject params);
};
