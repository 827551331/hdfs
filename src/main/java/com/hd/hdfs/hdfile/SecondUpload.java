package com.hd.hdfs.hdfile;

import com.hd.hdfs.entity.FileInfo;

/**
 * 秒传工具类
 *
 * @author wang_yw
 */
public interface SecondUpload {

    /**
     * 检测文件是否符合秒传条件
     *
     * @param md5
     * @return
     */
    public FileInfo isFileExist(String md5);
}
