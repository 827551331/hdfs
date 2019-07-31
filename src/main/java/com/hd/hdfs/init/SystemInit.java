package com.hd.hdfs.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * 系统初始化项
 */
@Component
public class SystemInit implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(SystemInit.class);

    @Value("${file-store-path}")
    String fileStorePath;

    @Override
    public void run(String... args) throws Exception {
        logger.info("#1、检查文件存储路径");
        this.checkFilePath();
    }


    /**
     * 检查文件存储路径是否存在且合法
     */
    private void checkFilePath() {
        Path path = Paths.get(fileStorePath);
        if (Files.notExists(path)) {
            logger.info("#路径不存在，创建路径,路径：{}", path.toAbsolutePath());
            try {
                // 如果目录不存在，则创建目录
                Files.createDirectory(path);
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.error("存储目录创建失败，原因：{}，请检查目录配置，文件会暂存在项目路径下", e1.getMessage());
            }
        } else {
            logger.info("#路径已存在，路径：{}", path.toAbsolutePath());
        }
    }
}
