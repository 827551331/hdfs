package com.hd.hdfs.hdfile;

/**
 * 处理文件
 */
public interface HandleFile {

    /**
     * 缓存文件到缓冲区
     */
    public void cacheFile();

    /**
     * 压缩文件
     */
    public void compressFile();
}
