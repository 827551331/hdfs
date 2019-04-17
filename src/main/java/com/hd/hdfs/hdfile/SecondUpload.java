package com.hd.hdfs.hdfile;

import org.springframework.web.multipart.MultipartFile;

/**
 * 秒传工具类
 *
 * @author wang_yw
 */
public interface SecondUpload {

    /**
     * 检测文件是否符合秒传条件
     *
     * @param multipartFile
     * @return
     */
    public Boolean isFileExist(MultipartFile multipartFile);
}
