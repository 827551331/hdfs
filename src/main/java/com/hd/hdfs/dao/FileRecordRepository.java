package com.hd.hdfs.dao;

import com.hd.hdfs.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件记录DAO
 *
 * @author wang_yw
 * @date 20190419
 */
@Repository
public interface FileRecordRepository extends JpaRepository<FileRecord, Integer> {
}
