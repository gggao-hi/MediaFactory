# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)
SET(CMAKE_BUILD_TYPE "Debug")
# Declares and names the project.

project("handler")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
set(LIB_SRC_DIR ../../../libs/${CMAKE_ANDROID_ARCH_ABI})
include_directories(include)

add_library(libavcodec_lib SHARED IMPORTED)
set_target_properties(libavcodec_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libavcodec.so)

add_library(libavdevice_lib SHARED IMPORTED)
set_target_properties(libavdevice_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libavdevice.so)

add_library(libavfilter_lib SHARED IMPORTED)
set_target_properties(libavfilter_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libavfilter.so)

add_library(libavformat_lib SHARED IMPORTED)
set_target_properties(libavformat_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libavformat.so)

add_library(libavutil_lib SHARED IMPORTED)
set_target_properties(libavutil_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libavutil.so)

add_library(libswresample_lib SHARED IMPORTED)
set_target_properties(libswresample_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libswresample.so)

add_library(libswscale_lib SHARED IMPORTED)
set_target_properties(libswscale_lib PROPERTIES IMPORTED_LOCATION ${LIB_SRC_DIR}/libswscale.so)

add_library( # Sets the name of the library.
        media_handler

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        image/image_handler.cpp
        video/video_handler.cpp
        video/video_dealer.cpp)

find_library(
        log-lib
        log)


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        media_handler
        jnigraphics
        ${libavcodec_lib}
        ${libavdevice_lib}
        ${libavfilter_lib}
        ${libavformat_lib}
        ${libavutil_lib}
        ${libswresample_lib}
        ${libswscale_lib}
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
