//
// Created by Gao, Guogang on 2023/11/1.
//

#include <sys/mman.h>
#include <fcntl.h>
#include <ctime>
#include <sstream>
#include "WriteFileManager.h"

ResultCode WriteFileManager::writeLog(LogInfo *logInfo) {

    return SUCCESS;
}

ResultCode WriteFileManager::clear() {
    return SUCCESS;
}

ResultCode WriteFileManager::deleteLogZip(std::string zipPath) {
    return SUCCESS;
}

ResultCode WriteFileManager::deleteLogFolder(std::string folderPath) {
    return SUCCESS;
}

void WriteFileManager::unregisterLogSizeChecker() {

}

void WriteFileManager::registerLogSizeChecker() {

}

ResultCode WriteFileManager::zipFolder(std::string folderPath, std::string outPath) {
    return SUCCESS;
}

ResultCode WriteFileManager::openLogFile(std::string fileName, int &fd) {
    std::string path = filePath(fileName);
    fd = open(path.c_str(), O_CREAT | O_WRONLY, S_IRWXO);
    if (fd == -1) {
        return FAILED;
    }
    return SUCCESS;
}

std::string WriteFileManager::filePath(std::string fileName) {
    std::string root = this->logConfig.rootPath;
    std::unique_ptr<std::tm> time = std::make_unique<std::tm>();
    timelocal(time.get());
    std::stringstream parentPath;
    parentPath << root << "\\" << time->tm_year << time->tm_mon << time->tm_mday << time->tm_hour
               << time->tm_min
               << time->tm_sec << "\\" << fileName;
    return parentPath.str();
}

