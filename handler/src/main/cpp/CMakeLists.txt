# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)
SET(CMAKE_BUILD_TYPE "Debug")
# Declares and names the project.
add_compile_options(-g)

project("handler")

add_library( # Sets the name of the library.
        handler

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        image/image_handler.cpp
        image/image_handler.h
        video/video_handler.cpp
        video/video_dealer.cpp
        video/video_dealer.h
        video/video_handler.h)
message("${CMAKE_CURRENT_SOURCE_DIR}")
message("${CMAKE_ANDROID_ARCH_ABI}")
# CMAKE_CURRENT_SOURCE_DIR code source的目录，cpp
set(distribution_DIR ${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_ANDROID_ARCH_ABI}/lib)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_ANDROID_ARCH_ABI}/include)
add_library( avcodec SHARED IMPORTED)
set_target_properties( avcodec PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libavcodec.so)

add_library( avdevice SHARED IMPORTED)
set_target_properties( avdevice PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libavdevice.so)

add_library( avfilter SHARED IMPORTED)
set_target_properties( avfilter PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libavfilter.so)

add_library( avformat SHARED IMPORTED)
set_target_properties( avformat PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libavformat.so)

add_library( avutil SHARED IMPORTED)
set_target_properties( avutil PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libavutil.so)

add_library( swscale SHARED IMPORTED)
set_target_properties( swscale PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libswscale.so)

add_library( swresample SHARED IMPORTED)
set_target_properties( swresample PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libswresample.so)

find_library(
        log-lib
        log)


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        handler
        avcodec
        avdevice
        avfilter
        avformat
        avutil
        swscale
        swresample
        jnigraphics
        ${log-lib})
