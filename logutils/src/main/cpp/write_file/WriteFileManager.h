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
    std::string timestamp;
    std::string tag;
    std::string level;
    std::string message;
    std::string pid;
    std::string tid;
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
    std::string rootPath;
    Duration retentionDuration;
    FileSize maxSizeOfAllZip;
    FileSize oneLogFileSize;
    int maxFileCountInFolder;
};

class WriteFileManager {
private:
    LogConfig logConfig;

    ResultCode openLogFile(std::string fileName, int &fd);

    ResultCode zipFolder(std::string folderPath, std::string outPath);

    void registerLogSizeChecker();

    void unregisterLogSizeChecker();

    ResultCode deleteLogFolder(std::string folderPath);

    ResultCode deleteLogZip(std::string zipPath);

    std::string filePath(std::string fileName);

    ResultCode clear();

public:
    void setConfig(LogConfig logConfig) {
        this->logConfig = logConfig;
    }

    ResultCode writeLog(LogInfo *logInfo);
};


#endif //MEDIAFACTORY_WRITEFILEMANAGER_H
