//
// Created by Gao, Guogang on 2023/11/1.
//

#ifndef MEDIAFACTORY_WRITEFILEMANAGER_H
#define MEDIAFACTORY_WRITEFILEMANAGER_H

#include <stdio.h>
#include <string>
#include <string_view>

enum ResultCode {
    SUCCESS = 0, FAILED = -1
};
struct LogInfo {
    std::string_view timestamp;
    std::string_view tag;
    std::string_view level;
    std::string_view message;
    std::string_view pid;
    std::string_view tid;
    std::exception err;
};
enum DurationType {
    Day, Hour, Minute, Second
};
struct Duration {
    long value;
    DurationType type;
};
enum FileSizeUnit {
    KB, MB, GB
};
struct FileSize {
    int size;
    FileSizeUnit unit;
};
struct LogConfig {
    std::string_view folderPath;
    Duration retentionDuration;
    FileSize maxSizeOfAllZip;
    FileSize oneLogFileSize;
    int maxFileCountInFolder;
};

class WriteFileManager {
private:
    LogConfig logConfig;

    ResultCode createLogFile(std::string_view filePath);

    ResultCode zipFolder(std::string_view folderPath, std::string_view outPath);

    void registerLogSizeChecker();

    void unregisterLogSizeChecker();

    ResultCode deleteLogFolder(std::string_view folderPath);

    ResultCode deleteLogZip(std::string_view zipPath);

    ResultCode clear();

public:
    void setConfig(LogConfig logConfig) {
        this->logConfig = logConfig;
    }

    ResultCode writeLog(LogInfo *logInfo);
};


#endif //MEDIAFACTORY_WRITEFILEMANAGER_H
