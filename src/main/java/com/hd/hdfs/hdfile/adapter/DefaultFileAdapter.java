package com.hd.hdfs.hdfile.adapter;

import com.hd.hdfs.hdfile.DownLoadFile;
import com.hd.hdfs.hdfile.StoreFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 默认存储实现类
 *
 * @author wang_yw
 * @date 2019-03-20
 */
@Component
public class DefaultFileAdapter implements StoreFile, DownLoadFile {

    @Value("${file-store-path}")
    String fileStorePath;

    /**
     * 存储文件--MultipartFile
     */
    @Override
    public String saveFile(MultipartFile file) {
        if (!file.isEmpty()) {
            // 获得原始文件名+格式
            String fileName = file.getOriginalFilename();
            //截取文件名
            String fname = fileName.substring(0, fileName.lastIndexOf("."));
            //截取文件格式
            String format = fileName.substring(fileName.lastIndexOf(".") + 1);
            //获取当前时间(精确到毫秒)
            long MS = System.currentTimeMillis();
            String timeMS = String.valueOf(MS);
            //原文件名+当前时间戳作为新文件名
            String newName = fname + "_" + timeMS + "." + format;
            File f = new File(fileStorePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fileURI = fileStorePath + newName;
            if (!file.isEmpty()) {
                try {
                    File file_o = new File(fileURI);
                    File zipFile = new File(fileURI + ".zip");
                    InputStream in = file.getInputStream();
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                    zos.putNextEntry(new ZipEntry(file_o.getName()));
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = in.read(bytes)) != -1) {
                        zos.write(bytes, 0, len);
                    }
                    in.close();
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return newName;
        } else {
            return "上传失败，文件为空.";
        }
    }

    /**
     * 存储多文件
     *
     * @param files
     * @return
     */
    @Override
    public String[] saveFiles(MultipartFile[] files) {
        List<String> fileNameArray = new ArrayList<>(20);
        for (int i = 0; i < files.length; i++) {
            fileNameArray.add(this.saveFile(files[i]));
        }
        return fileNameArray.toArray(new String[fileNameArray.size()]);
    }

    /**
     * 下载文件
     */
    @Override
    public void downloadFile(String fileName, HttpServletResponse httpServletResponse) {
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

            while ((temp = is.read(bytes)) != -1) {
                os.write(bytes, 0, temp);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
