package com.hd.hdfs.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 文件信息
 *
 * @author wang_yw
 * @date 20190419
 */
@Data
@Entity
@Table(name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long size;
    private String type;
    private String md5;
    private String path;
    private Date uploadTime;
}
