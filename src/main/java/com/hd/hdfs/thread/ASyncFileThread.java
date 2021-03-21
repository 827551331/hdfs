package com.hd.hdfs.thread;

import com.hd.hdfs.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * description: ASyncFileThread
 * date: 2021/3/18 16:34
 * author: wang_yw
 * version: 1.0
 */
public class ASyncFileThread implements Runnable {


    private MultipartFile file;
    private String fileStorePath;

    public ASyncFileThread(MultipartFile file, String fileStorePath) {
        this.file = file;
        this.fileStorePath = fileStorePath;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
// 获得原始文件名+格式
        String fileName = file.getOriginalFilename();
        //截取文件名
        String fname = FileUtil.getName(fileName);
        //截取文件格式
        String format = FileUtil.getType(fileName);
        //获取当前时间(精确到毫秒)
        String timeMS = String.valueOf(System.currentTimeMillis());
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
                file.transferTo(file_o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
