package com.hd.hdfs.hdfile.adapter;

import com.hd.hdfs.dao.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FixedNameFileAdapter extends DefaultFileAdapter {

    @Value("${file-store-path}")
    String fileStorePath;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private FileInfoRepository fileInfoRepository;


    /**
     * 存储文件--MultipartFile
     */
    @Override
    public String saveFile(MultipartFile file, String md5) {

        if (!file.isEmpty()) {
            // 获得原始文件名+格式
            String fileName = file.getOriginalFilename();
            //截取文件名
            String fname = fileName.substring(0, fileName.lastIndexOf("."));
            //截取文件格式
            String format = fileName.substring(fileName.lastIndexOf(".") + 1);
            //原文件名+md5作为新文件名
            String newName = fname + "." + format;
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
                    byte[] bytes = new byte[2048];
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

            //返回文件名进行编码防止下载的时候因为部分字符导致下载失败
            try {
                newName = URLEncoder.encode(newName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return newName;
        } else {
            return "上传失败，文件为空.";
        }
    }

}
