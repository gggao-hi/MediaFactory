//
// Created by Administrator on 2022/1/8.
//
#include <map>

#include "video_dealer.h"

using namespace std;

int VideoHandler::decode(jobject params, jobject surface) {
    LOGD("decode:", "decode");
    int videoIndex = -1;
    int ret;
    clock_t time_start, time_end;
    double time_duration = 0.0;
    map<string, string> *param = parseParams(params);
    string videoPath = param->find("videoPath")->second;

    LOGD("decode:", "input :%s\n", videoPath.c_str());
    LOGD("decode:", "avformat_open_input");

    if (avformat_open_input(&pFormatCtx, videoPath.c_str(), av_find_input_format("mp4"), nullptr) != 0) {
        LOGE("decode:", "couldn't open: %s \n", videoPath.c_str());
        return -1;
    }
    LOGD("decode:", "avformat_open_input finish");
    if (pFormatCtx == nullptr) {
        LOGE("decode:", "pFormatCtx is null");
        return -1;
    }
    if (pFormatCtx->streams == nullptr) {
        LOGE("decode:", "pFormatCtx->streams is null");
        return -1;
    }
    LOGD("decode:", "avformat_find_stream_info");
    avformat_find_stream_info(pFormatCtx, nullptr);
    for (int i = 0; i < pFormatCtx->nb_streams; i++) {
        if (pFormatCtx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            videoIndex = i;
            break;
        }
    }
    LOGD("decode:", "avformat_find_stream_info finish videoIndex:%d", videoIndex);

    if (pFormatCtx->streams[videoIndex]->codecpar == nullptr) {
        LOGE("decode:", "pFormatCtx->streams[videoIndex]->codecpar is null");
        return -1;
    }
    LOGD("decode:", "avcodec_find_decoder");
    pCodec = avcodec_find_decoder(pFormatCtx->streams[videoIndex]->codecpar->codec_id);
    if (pCodec == nullptr) {
        LOGE("decode:", "couldn't find codec \n");
        return -1;
    }
    LOGD("decode:", "avcodec_alloc_context3");
    pCodecCtx = avcodec_alloc_context3(pCodec);
    if (pCodecCtx == nullptr) {
        LOGE("decode:", "couldn't alloc codec context\n");
        return -1;
    }


    int result = avcodec_parameters_to_context(pCodecCtx,
                                               pFormatCtx->streams[videoIndex]->codecpar);
    if (result != 0) {
        LOGE("decode:", "avcodec_find_decoder failed, code:%d", result);

    }
    LOGD("decode:", "avcodec_open2");

    if (avcodec_open2(pCodecCtx, pCodec, nullptr) < 0) {
        LOGE("decode:", "couldn't open codec \n");
        return -1;
    }
    LOGD("decode:", "avcodec_open2 finish");
    pFrame = av_frame_alloc();
    pFrameRGBA = av_frame_alloc();

    packet = (AVPacket *) av_malloc(sizeof(AVPacket));
    LOGD("decode:", "av_malloc AVPacket finish");

    time_start = clock();
    pANWindow = ANativeWindow_fromSurface(env, surface);
    ANativeWindow_setBuffersGeometry(pANWindow, pCodecCtx->width, pCodecCtx->height,
                                     WINDOW_FORMAT_RGBA_8888);
    ANativeWindow_Buffer buffer;
    int window_width = ANativeWindow_getWidth(pANWindow);
    int window_height = ANativeWindow_getHeight(pANWindow);
    LOGD("decode:", "width:%d;height:%d,window with:%d,window height:%d",
         pCodecCtx->width, pCodecCtx->height, window_width, window_height);
    imgConvertCtx = sws_getContext(pCodecCtx->width, pCodecCtx->height, pCodecCtx->pix_fmt,
                                   window_width, window_height, AV_PIX_FMT_RGBA,
                                   SWS_POINT, nullptr, nullptr, nullptr);

    while (av_read_frame(pFormatCtx, packet) >= 0) {
        if (packet->stream_index != videoIndex) { continue; }
        ret = avcodec_send_packet(pCodecCtx, packet);
        if (ret < 0) {
            LOGE("decode:", "decoder error %d \n", ret);
            return -1;
        }
        while (avcodec_receive_frame(pCodecCtx, pFrame) == 0) {
            auto *outBuffer = (unsigned char *) av_malloc(
                    av_image_get_buffer_size(AV_PIX_FMT_RGBA, pCodecCtx->width, pCodecCtx->height,
                                             1));
            av_image_fill_arrays(pFrameRGBA->data, pFrameRGBA->linesize, outBuffer, AV_PIX_FMT_RGBA,
                                 pCodecCtx->width, pCodecCtx->height, 1);

            sws_scale(imgConvertCtx, pFrame->data, pFrame->linesize, 0, pCodecCtx->height,
                      pFrameRGBA->data, pFrameRGBA->linesize);

            ANativeWindow_lock(pANWindow, &buffer, nullptr);
            auto *destBuffer = reinterpret_cast<uint8_t *>(buffer.bits);
            int srcLine = pFrame->linesize[0];
            int dstLine = buffer.stride * 4;
            for (int i = 0; i < pCodecCtx->height; i++) {
                memcpy(destBuffer + i * dstLine, outBuffer + i * srcLine, srcLine);
            }
            ANativeWindow_unlockAndPost(pANWindow);
        }

    }

    time_end = clock();
    time_duration = time_end - time_start;
    LOGD("decode:", "decode duration: %f \n", time_duration / (1000 * 1000));

    av_free(packet);
    sws_freeContext(imgConvertCtx);
    av_frame_free(&pFrame);
    av_frame_free(&pFrameRGBA);
    avcodec_close(pCodecCtx);
    ANativeWindow_release(pANWindow);
    avformat_close_input(&pFormatCtx);

    return 0;

}

int VideoHandler::splitVideo(jobject params) {
    LOGD("VideoHandler:", "splitVideo");

    return 0;
}

int VideoHandler::jointVideo(jobject params) {
    LOGD("VideoHandler:", "jointVideo");
    return 0;
}

int VideoHandler::addInk(jobject params) {
    LOGD("VideoHandler:", "addInk");
    return 0;
}

