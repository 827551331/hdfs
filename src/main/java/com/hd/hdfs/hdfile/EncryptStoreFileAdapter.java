package com.hd.hdfs.hdfile;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 加密存储实现类
 *
 * @author wang_yw
 * @date 2019-03-20
 */
@Component
public class EncryptStoreFileAdapter implements StoreFile {

    private static final int key = 0x99;

    /**
     * 存储文件(^操作加密，性能太差)
     *
     * @param file
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
            String videoName = fname + "_" + timeMS + "." + format;
            String filelocalPath = "F://upload//";
            File f = new File(filelocalPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!file.isEmpty()) {
                try {
                    File file_o = new File(filelocalPath + videoName);
                    File zipFile = new File(filelocalPath + videoName + ".zip");
                    InputStream in = file.getInputStream();
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                    zos.putNextEntry(new ZipEntry(file_o.getName()));
                    zos.setComment("test compress");
                    int len;
                    while ((len = in.read()) != -1) {
                        zos.write(len ^ key);
                    }
                    in.close();
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "上传成功";
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
        return new String[0];
    }
}
