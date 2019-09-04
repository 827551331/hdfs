package com.hd.hdfs.hdfile.adapter;

import com.hd.hdfs.hdfile.LoadFile;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Component
public class LoadPicAdapter implements LoadFile {

    @Value("${file-store-path}")
    String fileStorePath;

    @Autowired
    private HttpServletResponse httpServletResponse;

    /**
     * 预览文件
     *
     * @param fileName
     */
    @Override
    public void loadFile(String fileName) {
        String fileDownLoadName = "";
        try {
            fileDownLoadName = new String(fileName.getBytes(), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //增加http头部，让浏览器识别下载响应
        httpServletResponse.addHeader("Content-Type", "application/octet-stream");
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename* = UTF-8''" + fileDownLoadName);

        byte[] bytes = new byte[1024];
        int temp = 0;
        try {
            ZipFile zipFile = new ZipFile(new File(fileStorePath + fileName + ".zip"));
            Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
            OutputStream os = httpServletResponse.getOutputStream();
            InputStream is = null;

            is = zipFile.getInputStream(enumeration.nextElement());

            Thumbnails.of(is).size(600,600).toOutputStream(os);

            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
