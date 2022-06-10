package com.ggg.share_remote


/**
 * Created by  gggao on 6/9/2022.
 */
data class FileItem(val name: String, val id: String, val type: FileType)

sealed class FileType(val icon: Int) {
    class PDF : FileType(R.drawable.ic_pdf)
    class WORD : FileType(R.drawable.ic_word)
    class EXCEL : FileType(R.drawable.ic_excel)
    class PPT : FileType(R.drawable.ic_ppt)
    class TXT : FileType(R.drawable.ic_txt)
    class VIDEO : FileType(R.drawable.ic_video)
    class PICTURE : FileType(R.drawable.ic_picture)
    class FOLDER : FileType(R.drawable.ic_folder)
}

data class RemoteBean(
    val isSuccess: Boolean,
    val fileItems: List<FileItem>? = null,
    val folders: List<FileItem>? = null
)

sealed class ResponseResult {
    data class Success(val files: List<FileItem>) : ResponseResult()
    data class Failed(val code: Int, val msg: String) : ResponseResult()
}