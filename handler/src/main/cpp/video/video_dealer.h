//
// Created by Administrator on 2022/1/8.
//
#include <string>
#include <vector>
#include <map>
#include "../commons/log.h"

using namespace std;
#ifndef MEDIAFACTORY_VIDEO_DEALER_H
#define MEDIAFACTORY_VIDEO_DEALER_H
struct SplitPath {
    string videoPath;
    string soundPath;
};

class VideoHandler {
public:
    static SplitPath *splitVideo(string videoPath);

    static string *jointVideo(vector<string> videoPath);

    static string *addInk(map<string, string> paths);
};

#endif //MEDIAFACTORY_VIDEO_DEALER_H
