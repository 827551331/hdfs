package com.hd.hdfs.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 文件记录
 *
 * @author wang_yw
 * @date 20190419
 */
@Data
@Entity
@Table(name = "file_record")
public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer fileInfoId;
    private Integer sysId;
    private Integer tenantId;
    private Integer fileState;
    private Date uploadTime;
    private String uploadPerson;
}
