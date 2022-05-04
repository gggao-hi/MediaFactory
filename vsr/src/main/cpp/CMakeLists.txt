cmake_minimum_required(VERSION 3.10.2)

project("vsr")
SET(CMAKE_BUILD_TYPE "Debug")
# Declares and names the project.
add_compile_options(-g)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_ANDROID_ARCH_ABI}/include)
# CMAKE_CURRENT_SOURCE_DIR code source的目录，cpp
set(distribution_DIR ${CMAKE_CURRENT_SOURCE_DIR}/${CMAKE_ANDROID_ARCH_ABI}/libs)

add_library( base SHARED IMPORTED)
set_target_properties( base PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-base.so)
add_library( chain SHARED IMPORTED)
set_target_properties( chain PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-chain.so)
add_library( cudamatrix SHARED IMPORTED)
set_target_properties( cudamatrix PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-cudamatrix.so)
add_library( decoder SHARED IMPORTED)
set_target_properties( decoder PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-decoder.so)
add_library( feat SHARED IMPORTED)
set_target_properties( feat PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-feat.so)
add_library( fstext SHARED IMPORTED)
set_target_properties( fstext PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-fstext.so)
add_library( gmm SHARED IMPORTED)
set_target_properties( gmm PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-gmm.so)
add_library( hmm SHARED IMPORTED)
set_target_properties( hmm PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-hmm.so)
add_library( ivector SHARED IMPORTED)
set_target_properties( ivector PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-ivector.so)
add_library( kws SHARED IMPORTED)
set_target_properties( kws PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-kws.so)
add_library( lat SHARED IMPORTED)
set_target_properties( lat PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-lat.so)
add_library( lm SHARED IMPORTED)
set_target_properties( lm PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-lm.so)
add_library( matrix SHARED IMPORTED)
set_target_properties( matrix PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-matrix.so)
add_library( nnet SHARED IMPORTED)
set_target_properties( nnet PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-nnet.so)
add_library( nnet2 SHARED IMPORTED)
set_target_properties( nnet2 PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-nnet2.so)
add_library( nnet3 SHARED IMPORTED)
set_target_properties( nnet3 PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-nnet3.so)
add_library( online2 SHARED IMPORTED)
set_target_properties( online2 PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-online2.so)
add_library( rnnlm SHARED IMPORTED)
set_target_properties( rnnlm PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-rnnlm.so)
add_library( transform SHARED IMPORTED)
set_target_properties( transform PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-transform.so)
add_library( tree SHARED IMPORTED)
set_target_properties( tree PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-tree.so)
add_library( util SHARED IMPORTED)
set_target_properties( util PROPERTIES IMPORTED_LOCATION ${distribution_DIR}/libkaldi-util.so)

add_library( # Sets the name of the library.
        vsr

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        vsr.cpp)

find_library(
        log-lib

        log)

target_link_libraries( # Specifies the target library.
        vsr
        base
        chain
        cudamatrix
        decoder
        feat
        fstext
        gmm
        hmm
        ivector
        kws
        lat
        lm
        matrix
        nnet
        nnet2
        nnet3
        online2
        rnnlm
        transform
        tree
        util
        ${log-lib})