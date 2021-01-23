package com.hd.hdfs.schedule;

import com.hd.hdfs.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * description: 定时任务
 * date: 2021/1/23 11:30
 * author: wang_yw
 * version: 1.0
 */
@Component
public class ScheduleWork {

    @Value("${file-store-path}")
    String fileStorePath;

    @Scheduled(cron = "0/3 * * * * ?")
    public void perSecond() {
        //进入每3s执行定时任务
        this.compressFile();
    }

    private void compressFile() {
        File directory = new File(fileStorePath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles((file, name) -> !name.endsWith(".zip") && !name.endsWith(".temp"));
            for (File file : files) {
                try {
                    FileInputStream fis = new FileInputStream(file);

                    File zipFile = new File(fileStorePath + FileUtil.getName(file.getName()) + ".zip");
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    IOUtils.copy(fis, zos);
                    zos.closeEntry();

                    fis.close();
                    zos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//                    file.delete();
                }
            }
        }
    }
}