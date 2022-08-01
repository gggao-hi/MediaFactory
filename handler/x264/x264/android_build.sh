export ANDROID_NDK=/mnt/d/documents/code/linux_station/android-ndk-r21e
export TOOLCHAIN=${ANDROID_NDK}/toolchains/llvm/prebuilt/linux-x86_64
export SYSROOT=${TOOLCHAIN}/sysroot
export CROSS_PREFIX=${TOOLCHAIN}/bin/aarch64-linux-android21-clang

./configure \
    --prefix=./so \
    --cross-prefix=${CROSS_PREFIX} \
    --sysroot=${SYSROOT} \
    --enable-static \
    --enable-shared \
    --enable-pic \
    --disable-asm \
    --host=arm-linux

make clean
make 
make install