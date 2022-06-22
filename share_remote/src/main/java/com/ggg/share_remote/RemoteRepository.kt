package com.ggg.share_remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Created by  gggao on 6/9/2022.
 */
class RemoteRepository {
    suspend fun fetchFiles(url: String): ResponseResult {
        return withContext(Dispatchers.IO) {
            mock(url)
        }
    }

    private fun mock(url: String): ResponseResult {
        return ResponseResult.Success(
            when (url) {
                "test7" ->
                    listOf(
                        FileItem("test1", "test1", FileType.PDF()),
                        FileItem("test2", "test2", FileType.VIDEO()),
                        FileItem("test3", "test3", FileType.PICTURE()),
                        FileItem("test4", "test4", FileType.PPT()),
                        FileItem("test10", "test10", FileType.FOLDER())
                    )
                "test8" ->
                    listOf(
                        FileItem("test1", "test1", FileType.PDF()),
                        FileItem("test2", "test2", FileType.VIDEO()),
                        FileItem("test3", "test3", FileType.PICTURE()),
                        FileItem("test4", "test4", FileType.PPT()),
                        FileItem("test5", "test5", FileType.WORD()),
                        FileItem("test6", "test6", FileType.EXCEL()),
                        FileItem("test10", "test10", FileType.FOLDER())
                    )
                "test9" ->
                    listOf(
                        FileItem("test1", "test1", FileType.PDF()),
                        FileItem("test2", "test2", FileType.VIDEO()),
                        FileItem("test3", "test3", FileType.PICTURE()),
                        FileItem("test4", "test4", FileType.PPT()),
                        FileItem("test5", "test5", FileType.WORD())

                    )
                "test10" ->
                    listOf(
                        FileItem("test1", "test1", FileType.PDF()),
                        FileItem("test2", "test2", FileType.VIDEO()),
                        FileItem("test3", "test3", FileType.PICTURE()),
                        FileItem("test4", "test4", FileType.PPT()),
                        FileItem("test5", "test5", FileType.WORD()),
                        FileItem("test6", "test6", FileType.EXCEL())
                    )
                else -> listOf(
                    FileItem("test1", "test1", FileType.PDF()),
                    FileItem("test2", "test2", FileType.VIDEO()),
                    FileItem("test3", "test3", FileType.PICTURE()),
                    FileItem("test4", "test4", FileType.PPT()),
                    FileItem("test5", "test5", FileType.WORD()),
                    FileItem("test6", "test6", FileType.EXCEL()),
                    FileItem("test7", "test7", FileType.FOLDER()),
                    FileItem("test8", "test8", FileType.FOLDER()),
                    FileItem("test9", "test9", FileType.FOLDER()),
                    FileItem("test10", "test10", FileType.FOLDER()),
                )
            }

        )
    }
}