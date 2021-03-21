package com.hd.hdfs.service.impl;

import com.hd.hdfs.dao.FileInfoRepository;
import com.hd.hdfs.dao.FileRecordRepository;
import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.entity.FileRecord;
import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import com.hd.hdfs.hdfile.adapter.SecondUploadAdapter;
import com.hd.hdfs.service.FileHandleService;
import com.hd.hdfs.thread.ASyncFileThread;
import com.hd.hdfs.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GeneralFileHandleServiceImpl implements FileHandleService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralFileHandleServiceImpl.class);

    @Value("${file-store-path}")
    String fileStorePath;

    @Autowired
    private SecondUploadAdapter secondUploadAdapter;

    @Autowired
    private DefaultFileAdapter defaultFileAdapter;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileRecordRepository fileRecordRepository;


    /**
     * 文件上传处理
     *
     * @return
     */
    @Override
    public List<FileInfo> uploadFile(MultipartFile[] files) {

        List<FileInfo> result = new ArrayList<>();


        for (MultipartFile file : files) {
            //获取文件MD5(后端做 MD5 验证，对小文件收益不大，大文件计算时间太长，负收益 20210318，取消此流程)
            //String md5 = FileUtil.getMd5(file);
            //FileInfo fileInfo = secondUploadAdapter.isFileExist(md5);

            String md5 = "";
            FileInfo fileInfo = null;

            FileRecord fileRecord = new FileRecord();
            //如果文件已经存在
            if (fileInfo != null) {
                fileInfo.setUsedName(file.getOriginalFilename());
                result.add(fileInfo);
            } else {
                //异步处理文件落盘（IO）
                System.out.println("异步处理文件");
                new Thread(new ASyncFileThread(file, fileStorePath)).start();

                fileInfo = new FileInfo();
                fileInfo.setUsedName(file.getOriginalFilename());
                //fileInfo.setName(defaultFileAdapter.saveFile(file, md5));
                fileInfo.setName(this.getFileName(file));
                fileInfo.setSize(file.getSize());
                fileInfo.setMd5(md5);
                fileInfo.setType(file.getContentType());
                fileInfo.setPath(fileStorePath);
                fileInfo.setUploadTime(new Date());
                fileInfo = fileInfoRepository.saveAndFlush(fileInfo);
                result.add(fileInfo);
            }

            fileRecord.setFileInfoId(fileInfo.getId());
            fileRecord.setFileState(1);
            fileRecord.setUploadTime(new Date());
            fileRecordRepository.saveAndFlush(fileRecord);
        }


        return result;
    }

    /**
     * 根据文件名下载文件
     *
     * @param fileName
     */
    @Override
    public void downloadFile(String fileName) {
        FileInfo fileInfo = fileInfoRepository.findByName(URLEncoder.encode(fileName));
        logger.info("文件信息：{}", fileInfo.toString());
        defaultFileAdapter.downloadFileByFileInfo(fileInfo);
    }

    /**
     * 根据文件 id 下载文件
     *
     * @param id
     */
    @Override
    public void downloadFileById(String id) {
        FileInfo fileInfo = fileInfoRepository.findById(Integer.valueOf(id)).get();
        logger.info("文件信息：{}", fileInfo.toString());
        defaultFileAdapter.downloadFileByFileInfo(fileInfo);
    }

    private String getFileName(MultipartFile file) {
        // 获得原始文件名+格式
        String fileName = file.getOriginalFilename();
        //截取文件名
        String fname = fileName.substring(0, fileName.lastIndexOf("."));
        //截取文件格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取当前时间(精确到毫秒)
        String timeMS = String.valueOf(System.currentTimeMillis());
        //原文件名+当前时间戳作为新文件名
        String newName = fname + "_" + timeMS + "." + format;

        return newName;
    }
}
