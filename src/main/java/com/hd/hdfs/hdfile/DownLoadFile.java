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
     * 预览文件
     */
    void loadFile(String fileName);
}
