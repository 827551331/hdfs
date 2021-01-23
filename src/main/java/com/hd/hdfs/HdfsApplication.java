package com.hd.hdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

///**
// * jar运行（内嵌tomcat方案）
// */
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class HdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HdfsApplication.class, args);
    }

}


/**
 * 切换tomcat部署方案
 */
//@EnableCaching
//@SpringBootApplication
//public class HdfsApplication extends SpringBootServletInitializer {
//
//    public static void main(String[] args) {
//        SpringApplication.run(HdfsApplication.class, args);
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(HdfsApplication.class);
//    }
//
//}
