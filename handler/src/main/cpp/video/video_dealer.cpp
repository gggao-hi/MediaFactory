//
// Created by Administrator on 2022/1/8.
//
#include <map>

#include "video_dealer.h"

using namespace std;

string *VideoHandler::decode(string videoPath) {
    LOGD("decode");
    int i, videoIndex;
    uint8_t *out_buffer;
    int y_size;
    int ret, go_picture;
    int frame_cnt;
    clock_t time_start, time_end;
    double time_duration = 0.0;
    if (avformat_open_input(&pFormatCtx, videoPath.c_str(), nullptr, nullptr) != 0) {
        LOGE("couldn't open: %s", videoPath.c_str());
    }
}

SplitPath *VideoHandler::splitVideo(string videoPath) {
    LOGD("splitVideo");
    auto *splitPath = new SplitPath;
    splitPath->videoPath = "video";
    splitPath->soundPath = "sound";
    return splitPath;
}

string *VideoHandler::jointVideo(vector<string> videoPath) {
    LOGD("jointVideo");
    auto *path = new string;
    return path;
}

string *VideoHandler::addInk(map<string, string> paths) {
    LOGD("addInk");
    auto *path = new string;
    return path;
}

