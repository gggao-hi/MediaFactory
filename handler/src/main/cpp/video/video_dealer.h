//
// Created by Administrator on 2022/1/8.
//
#include <string>
#include <vector>
#include <map>
#include <stdio.h>
#include <time.h>
#include "../commons/log.h"
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"
#include "libavutil/avutil.h"

using namespace std;
#ifndef MEDIAFACTORY_VIDEO_DEALER_H
#define MEDIAFACTORY_VIDEO_DEALER_H
struct SplitPath {
    string videoPath;
    string soundPath;
};

class VideoHandler {
private:
    AVFormatContext *pFormatCtx;
    AVCodecContext *pCodecCtx;
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

        pFormatCtx=avformat_alloc_context();

    }

    void unInit() {
        avformat_network_deinit();
    }

public:
    VideoHandler() {
        init();
    }

    ~VideoHandler() {
        unInit();
    }

    string *decode(string videoPath);

    SplitPath *splitVideo(string videoPath);

    string *jointVideo(vector<string> videoPath);

    string *addInk(map<string, string> paths);
};

#endif //MEDIAFACTORY_VIDEO_DEALER_H
