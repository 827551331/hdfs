package com.hd.hdfs.hdfile;

import org.springframework.web.multipart.MultipartFile;

/**
 * 存储文件
 */
public interface StoreFile {

    /**
     * 存储文件
     */
    public String saveFile(MultipartFile file);
}
