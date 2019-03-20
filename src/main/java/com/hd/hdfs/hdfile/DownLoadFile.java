package com.hd.hdfs.hdfile;

import javax.servlet.http.HttpServletResponse;

public interface DownLoadFile {

    /**
     * 下载文件
     */
    public void downloadFile(String fileName, HttpServletResponse httpServletResponse);
}
