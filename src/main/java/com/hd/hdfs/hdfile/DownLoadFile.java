package com.hd.hdfs.hdfile;

/**
 * 文件下载接口
 *
 * @author wang_yw
 */
public interface DownLoadFile {

    /**
     * 下载文件
     */
    void downloadFile(String fileName);

    /**
     * 批量下载下载文件
     */
    void batchDownloadFile(String[] fileNames);

    /**
     * 预览文件
     */
    void loadFile(String fileName);
}
