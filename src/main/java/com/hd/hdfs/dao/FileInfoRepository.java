package com.hd.hdfs.dao;

import com.hd.hdfs.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件信息
 *
 * @author wang_yw
 */
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {

    FileInfo findByMd5(String md5);
}
