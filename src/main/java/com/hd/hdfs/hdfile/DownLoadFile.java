package com.hd.hdfs.hdfile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载接口
 *
 * @author wang_yw
 */
public interface DownLoadFile {

    /**
     * 下载文件
     */
    public void downloadFile(String fileName, HttpServletResponse httpServletResponse);
}
