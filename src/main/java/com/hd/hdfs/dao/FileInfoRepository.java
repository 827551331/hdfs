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

    /**
     * 根据文件MD5查询文件信息
     *
     * @param md5 文件MD5值
     * @return 文件信息对象
     */
    FileInfo findByMd5(String md5);

    /**
     * 根据唯一文件名称查询文件信息
     *
     * @param fileName 唯一文件名称
     * @return 文件信息对象
     */
    FileInfo findByName(String fileName);
}
