
#https://gitee.com/hangliebe/resource/tree/master/shellorbat/ffmpeg

export NDK=/mnt/d/soft/linuxNDK/android-ndk-r21e-linux-x86_64/android-ndk-r21e
export SYSROOT=$NDK/platforms/android-24/arch-arm
export TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64
export CPU=arm
export PREFIX=../so
export ADDI_CFLAGS="-marm"

build_one()
{
    ./configure \
        --prefix=$PREFIX \
        --enable-shared \
        --enable-static \
        --disable-doc \
        --disable-ffprobe \
        --disable-ffserver \
        --disable-doc \
        --disable-symver \
        --enable-small \
        --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
        --target-os=android  \
        --arch=arm \
        --enable-cross-compile \
        --sysroot=$SYSROOT \
       --extra-cflags="-O3 -fPIC" \

    make clean
    make
    make install

   $TOOLCHAIN/bin/arm-linux-androideabi-ld \
    -rpath-link=$SYSROOT/usr/lib \
    -L$SYSROOT/usr/lib \
    -L$PREFIX/lib \
    -soname libffmpeg.so -shared -nostdlib -Bsymbolic --whole-archive --no-undefined -o \
   $PREFIX/libffmpeg.so \
    libavcodec/libavcodec.a \
    libavfilter/libavfilter.a \
    libswresample/libswresample.a \
    libavformat/libavformat.a \
    libavutil/libavutil.a \
    libswscale/libswscale.a \
    libavdevice/libavdevice.a \
    -lc -lm -lz -ldl -llog --dynamic-linker=/system/bin/linker \
    $TOOLCHAIN/lib/gcc/arm-linux-androideabi/4.9.x/libgcc.a
}

build_one