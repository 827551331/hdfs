package com.hd.hdfs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 文件工具类
 *
 * @author wang_yw
 */
public class FileUtil {
    private static Logger log = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 获取文件MD5值
     *
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            long start = System.currentTimeMillis();
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String md5_str = new BigInteger(1, digest).toString(16);

            long end = System.currentTimeMillis();
            System.out.println("MD5耗时：" + (end - start));
            return md5_str;
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return null;

    }
}
