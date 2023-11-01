//
// Created by Gao, Guogang on 2023/11/1.
//

#include <sys/mman.h>
#include <sys/file.h>
#include "WriteFileManager.h"

ResultCode WriteFileManager::writeLog(LogInfo *logInfo) {

    return SUCCESS;
}

ResultCode WriteFileManager::clear() {
    return SUCCESS;
}

ResultCode WriteFileManager::deleteLogZip(std::string_view zipPath) {
    return SUCCESS;
}

ResultCode WriteFileManager::deleteLogFolder(std::string_view folderPath) {
    return SUCCESS;
}

void WriteFileManager::unregisterLogSizeChecker() {

}

void WriteFileManager::registerLogSizeChecker() {

}

ResultCode WriteFileManager::zipFolder(std::string_view folderPath, std::string_view outPath) {
    return SUCCESS;
}

ResultCode WriteFileManager::createLogFile(std::string_view filePath) {

    return SUCCESS;
}

