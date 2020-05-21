package com.hd.hdfs.service.impl;

import com.hd.hdfs.dao.FileInfoRepository;
import com.hd.hdfs.dao.FileRecordRepository;
import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.entity.FileRecord;
import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import com.hd.hdfs.hdfile.adapter.SecondUploadAdapter;
import com.hd.hdfs.service.FileHandleService;
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
            //获取文件MD5
            String md5 = FileUtil.getMd5(file);
            FileInfo fileInfo = secondUploadAdapter.isFileExist(md5);
            FileRecord fileRecord = new FileRecord();
            //如果文件已经存在
            if (fileInfo != null) {
                fileInfo.setUsedName(file.getOriginalFilename());
                result.add(fileInfo);
            } else {
                fileInfo = new FileInfo();
                fileInfo.setUsedName(file.getOriginalFilename());
                fileInfo.setName(defaultFileAdapter.saveFile(file, md5));
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
}
