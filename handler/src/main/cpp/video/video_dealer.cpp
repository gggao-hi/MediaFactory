//
// Created by Administrator on 2022/1/8.
//
#include <map>

#include "video_dealer.h"

using namespace std;

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

