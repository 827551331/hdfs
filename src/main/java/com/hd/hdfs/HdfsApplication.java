package com.hd.hdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdfsApplication.class, args);
    }

}
