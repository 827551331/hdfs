package com.hd.hdfs.hdfile.adapter;

import com.hd.hdfs.dao.FileInfoRepository;
import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.hdfile.SecondUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecondUploadAdapter implements SecondUpload {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    /**
     * 检测文件是否符合秒传条件
     *
     * @param md5
     * @return
     */
    //@Cacheable("temporary_cache")
    @Override
    public FileInfo isFileExist(String md5) {
        return fileInfoRepository.findByMd5(md5);
    }
}

