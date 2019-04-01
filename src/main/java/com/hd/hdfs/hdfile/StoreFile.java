package com.hd.hdfs.hdfile;

import org.springframework.web.multipart.MultipartFile;

/**
 * 存储文件接口
 */
public interface StoreFile {

    /**
     * 存储文件（单个文件）
     */
    public String saveFile(MultipartFile file);

    /**
     * 存储多文件
     *
     * @param files
     * @return
     */
    public String[] saveFiles(MultipartFile[] files);
}
