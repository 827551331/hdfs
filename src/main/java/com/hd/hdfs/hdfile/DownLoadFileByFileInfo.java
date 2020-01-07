package com.hd.hdfs.hdfile;

import com.hd.hdfs.entity.FileInfo;

public interface DownLoadFileByFileInfo {

    /**
     * 下载文件
     *
     * @param fileInfo 文件信息对象
     */
    void downloadFileByFileInfo(FileInfo fileInfo);
}
