cmake_minimum_required(VERSION 3.10.2)
set(CMAKE_BUILD_TYPE "Debug")
add_compile_options("-g")
project("logUtils")

add_library(
        logUtils
        SHARED
        write_file/WriteFileManager.cpp
        write_file/WriteFileManager.h
        log_utils.cpp
)

find_library(
        log-lib
        log
)
target_link_libraries(
        logUtils
        ${log-lib}
        android
)