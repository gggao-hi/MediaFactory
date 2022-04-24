//
// Created by Administrator on 2022/1/8.
//
#include <map>

#include "video_dealer.h"

using namespace std;

int VideoHandler::decode(jobject params) {
    LOGD("decode:","decode");
    int videoIndex = -1;
    int y_size = 0;
    int ret, got_picture;
    int frame_cnt = 0;
    clock_t time_start, time_end;
    double time_duration = 0.0;
    map<string, string> *param = parseParams(params);
    string videoPath = param->find("videoPath")->second;
    string outPath = param->find("outPath")->second;
    LOGD("decode:","input :%s\n", videoPath.c_str());
    LOGD("decode:","avformat_open_input");
    if (avformat_open_input(&pFormatCtx, videoPath.c_str(), nullptr, nullptr) != 0) {
        LOGE("decode:","couldn't open: %s \n", videoPath.c_str());
        return -1;
    }
    if (pFormatCtx == nullptr) {
        LOGE("decode:","pFormatCtx is null");
        return -1;
    }
    if (pFormatCtx->streams == nullptr) {
        LOGE("decode:","pFormatCtx->streams is null");
        return -1;
    }
    avformat_find_stream_info(pFormatCtx, nullptr);
    for (int i = 0; i < pFormatCtx->nb_streams; i++) {
        if (pFormatCtx->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            videoIndex = i;
            break;
        }
    }
    LOGD("decode:","avcodec_alloc_context3");
    pCodecCtx = avcodec_alloc_context3(nullptr);
    if (pCodecCtx == nullptr) {
        LOGE("decode:","couldn't alloc codec context\n");
        return -1;
    }

    if (pFormatCtx->streams[videoIndex]->codecpar == nullptr) {
        LOGE("decode:","pFormatCtx->streams[videoIndex]->codecpar is null");
        return -1;
    }
    LOGD("decode:","avcodec_parameters_to_context\n");
    LOGD("decode:","avcodec_parameters_to_context codec format:%d\n",
         pFormatCtx->streams[videoIndex]->codecpar->format);
    LOGD("decode:","avcodec_parameters_to_context codec frame_size:%d\n",
         pFormatCtx->streams[videoIndex]->codecpar->frame_size);
    avcodec_parameters_to_context(pCodecCtx, pFormatCtx->streams[videoIndex]->codecpar);
    LOGD("decode:","avcodec_find_decoder");

    pCodec = avcodec_find_decoder(pCodecCtx->codec_id);
    if (pCodec == nullptr) {
        LOGE("decode:","couldn't find codec \n");
        return -1;
    }
    LOGD("decode:","avcodec_open2");

    if (avcodec_open2(pCodecCtx, pCodec, nullptr) < 0) {
        LOGE("decode:","couldn't open codec \n");
        return -1;
    }
    LOGD("decode:","avcodec_open2 finish");
    pFrame = av_frame_alloc();
    pFrameYUV = av_frame_alloc();

    unsigned char *outBuffer = (unsigned char *) av_malloc(
            av_image_get_buffer_size(AV_PIX_FMT_YUV420P, pCodecCtx->width, pCodecCtx->height, 1));
    av_image_fill_arrays(pFrameYUV->data, pFrameYUV->linesize, outBuffer, AV_PIX_FMT_YUV420P,
                         pCodecCtx->width, pCodecCtx->height, 1);

    packet = (AVPacket *) av_malloc(sizeof(AVPacket));
    LOGD("decode:","av_malloc AVPacket finish");
    LOGD("decode:","pCodecCtx->pix_fmt:");

    imgConvertCtx = sws_getContext(pCodecCtx->width, pCodecCtx->height, pCodecCtx->pix_fmt,
                                   pCodecCtx->width, pCodecCtx->height, AV_PIX_FMT_YUV420P,
                                   SWS_BICUBIC,
                                   nullptr, nullptr, nullptr);

    time_start = clock();
    fpYUV = fopen((outPath + "/out.yuv").c_str(), "wb+");
    LOGD("decode:","fopen finish");
    if (fpYUV == nullptr) {
        LOGE("decode:","couldn't open out path \n");
        return -1;
    }
    while (av_read_frame(pFormatCtx, packet) >= 0) {
        if (packet->stream_index != videoIndex) { continue; }
        ret = avcodec_send_packet(pCodecCtx, packet);
        if (ret < 0) {
            LOGE("decode:","decoder error %d \n", ret);
            return -1;
        }
        got_picture = avcodec_receive_frame(pCodecCtx, pFrame);
        if (got_picture == 0) {
            sws_scale(imgConvertCtx, pFrame->data, pFrame->linesize, 0, pCodecCtx->height,
                      pFrameYUV->data, pFrameYUV->linesize);
            y_size = pCodecCtx->width * pCodecCtx->height;
            fwrite(pFrameYUV->data[0], 1, y_size, fpYUV);
            fwrite(pFrameYUV->data[1], 1, y_size / 4, fpYUV);
            fwrite(pFrameYUV->data[2], 1, y_size / 4, fpYUV);
            frame_cnt++;
            fflush(fpYUV);
        }

    }

    fclose(fpYUV);

    time_end = clock();

    time_duration = time_end - time_start;
    LOGD("decode:","decode duration: %f \n", time_duration / (1000 * 1000));

    av_free(packet);
    sws_freeContext(imgConvertCtx);
    av_frame_free(&pFrame);
    av_frame_free(&pFrameYUV);
    avcodec_close(pCodecCtx);
    avformat_close_input(&pFormatCtx);
    LOGD("decode:","output :%s\n", outPath.c_str());

    return 0;

}

int VideoHandler::splitVideo(jobject params) {
    LOGD("VideoHandler:","splitVideo");

    return 0;
}

int VideoHandler::jointVideo(jobject params) {
    LOGD("VideoHandler:","jointVideo");
    return 0;
}

int VideoHandler::addInk(jobject params) {
    LOGD("VideoHandler:","addInk");
    return 0;
}

