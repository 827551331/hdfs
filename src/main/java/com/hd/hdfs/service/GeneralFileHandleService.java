package com.hd.hdfs.service;

import com.hd.hdfs.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通用文件处理
 *
 * @author wang_yw
 */
public interface GeneralFileHandleService {

    /**
     * 文件上传处理
     *
     * @return
     */
    public List<FileInfo> uploadFile(MultipartFile[] files);

    /**
     * 根据文件名下载文件
     *
     * @param fileName
     * @param httpServletResponse
     */
    public void downloadFile(String fileName, HttpServletResponse httpServletResponse);
}
