package com.hd.hdfs.service;

import com.hd.hdfs.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 通用文件处理
 *
 * @author wang_yw
 */
public interface FileHandleService {

    /**
     * 文件上传处理
     *
     * @return
     */
    List<FileInfo> uploadFile(MultipartFile[] files);

    /**
     * 根据文件名下载文件
     *
     * @param fileName
     */
    void downloadFile(String fileName);

    /**
     * 根据文件 id 下载文件
     *
     * @param id
     */
    void downloadFileById(String id);
}
