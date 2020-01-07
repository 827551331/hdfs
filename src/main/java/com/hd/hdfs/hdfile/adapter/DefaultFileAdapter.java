package com.hd.hdfs.hdfile.adapter;

import com.hd.hdfs.dao.FileInfoRepository;
import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.hdfile.DownLoadFile;
import com.hd.hdfs.hdfile.DownLoadFileByFileInfo;
import com.hd.hdfs.hdfile.StoreFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 默认存储实现类
 *
 * @author wang_yw
 * @date 2019-03-20
 */
@Component
public class DefaultFileAdapter implements StoreFile, DownLoadFile, DownLoadFileByFileInfo {

    @Value("${file-store-path}")
    String fileStorePath;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    /**
     * 存储文件--MultipartFile
     */
    @Override
    public String saveFile(MultipartFile file) {

        if (!file.isEmpty()) {
            // 获得原始文件名+格式
            String fileName = file.getOriginalFilename();
            //截取文件名
            String fname = fileName.substring(0, fileName.lastIndexOf("."));
            //截取文件格式
            String format = fileName.substring(fileName.lastIndexOf(".") + 1);
            //获取当前时间(精确到毫秒)
            long MS = System.currentTimeMillis();
            String timeMS = String.valueOf(MS);
            //原文件名+当前时间戳作为新文件名
            String newName = fname + "_" + timeMS + "." + format;
            File f = new File(fileStorePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fileURI = fileStorePath + newName;
            if (!file.isEmpty()) {
                try {
                    File file_o = new File(fileURI);
                    File zipFile = new File(fileURI + ".zip");
                    InputStream in = file.getInputStream();
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                    zos.putNextEntry(new ZipEntry(file_o.getName()));

                    //使用工具简化输入流向输出流的复制操作
                    IOUtils.copy(in, zos);

                    in.close();
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return newName;
        } else {
            return "上传失败，文件为空.";
        }
    }

    /**
     * 存储文件--MultipartFile
     */
    public String saveFile(MultipartFile file, String md5) {

        if (!file.isEmpty()) {
            // 获得原始文件名+格式
            String fileName = file.getOriginalFilename();
            //截取文件名
            String fname = fileName.substring(0, fileName.lastIndexOf("."));
            //截取文件格式
            String format = fileName.substring(fileName.lastIndexOf(".") + 1);
            //原文件名+md5作为新文件名
            String newName = fname + "_" + md5 + "." + format;
            File f = new File(fileStorePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            String fileURI = fileStorePath + newName;
            if (!file.isEmpty()) {
                try {
                    File file_o = new File(fileURI);
                    File zipFile = new File(fileURI + ".zip");
                    InputStream in = file.getInputStream();
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
                    zos.putNextEntry(new ZipEntry(file_o.getName()));
                    byte[] bytes = new byte[2048];
                    int len;
                    while ((len = in.read(bytes)) != -1) {
                        zos.write(bytes, 0, len);
                    }
                    in.close();
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //返回文件名进行编码防止下载的时候因为部分字符导致下载失败
            try {
                newName = URLEncoder.encode(newName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return newName;
        } else {
            return "上传失败，文件为空.";
        }
    }

    /**
     * 存储多文件
     *
     * @param files
     * @return
     */
    @Override
    public String[] saveFiles(MultipartFile[] files) {
        List<String> fileNameArray = new ArrayList<>(20);
        for (int i = 0; i < files.length; i++) {
            fileNameArray.add(this.saveFile(files[i]));
        }
        return fileNameArray.toArray(new String[fileNameArray.size()]);
    }

    /**
     * 下载文件
     */
    @Override
    public void downloadFile(String fileName) {
        //增加http头部，让浏览器识别下载响应
        httpServletResponse.addHeader("Content-Type", "application/octet-stream");
        try {
            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[1024];
        int temp = 0;
        try {
            ZipFile zipFile = new ZipFile(new File(fileStorePath + fileName + ".zip"));
            Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
            OutputStream os = httpServletResponse.getOutputStream();
            InputStream is = null;

            is = zipFile.getInputStream(enumeration.nextElement());

            while ((temp = is.read(bytes)) != -1) {
                os.write(bytes, 0, temp);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param fileInfo 文件信息对象
     */
    @Override
    public void downloadFileByFileInfo(FileInfo fileInfo) {


        //增加http头部，让浏览器识别下载响应
        httpServletResponse.addHeader("Content-Type", "application/octet-stream");
        try {
            httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + new String(fileInfo.getUsedName().getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[1024];
        int temp = 0;
        try {
            ZipFile zipFile = new ZipFile(new File(fileInfo.getPath() + fileInfo.getName() + ".zip"));
            Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
            OutputStream os = httpServletResponse.getOutputStream();
            InputStream is = null;

            is = zipFile.getInputStream(enumeration.nextElement());

            while ((temp = is.read(bytes)) != -1) {
                os.write(bytes, 0, temp);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量下载下载文件
     *
     * @param fileNames
     */
    @Override
    public void batchDownloadFile(String[] fileNames) {

        //增加http头部，让浏览器识别下载响应
        httpServletResponse.addHeader("Content-Type", "application/zip");
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename* = UTF-8''附件.zip");

        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 实例化 ZipOutputStream 对象
        try {
            zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInfo fileInfo = null;

        for (String fileName : fileNames) {

            fileInfo = new FileInfo();
            fileInfo.setName(fileName);
            Example<FileInfo> example = Example.of(fileInfo);
            FileInfo fileInfo_result = fileInfoRepository.findOne(example).get();
            String uesdName = fileInfo_result.getUsedName();

            try {
                fileName = URLDecoder.decode(fileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 创建 ZipEntry 对象
            ZipEntry zipEntryResult = null;
            try {
                ZipFile zipFileResource = new ZipFile(new File(fileStorePath + fileName + ".zip"));
                Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFileResource.entries();
                InputStream is = null;
                is = zipFileResource.getInputStream(enumeration.nextElement());

                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntryResult = new ZipEntry(uesdName);
                zipOutputStream.putNextEntry(zipEntryResult);

                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览文件
     *
     * @param fileName
     */
    @Override
    public void loadFile(String fileName) {
        String fileDownLoadName = "";
        try {
            fileDownLoadName = new String(fileName.getBytes(), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(fileDownLoadName);
        Example<FileInfo> example = Example.of(fileInfo);
        fileInfo = fileInfoRepository.findOne(example).get();

        //增加http头部，让浏览器识别下载响应
        httpServletResponse.addHeader("Content-Type", fileInfo.getType());
        httpServletResponse.addHeader("Content-Disposition", "inline;filename* = UTF-8''" + fileDownLoadName);

        byte[] bytes = new byte[1024];
        int temp = 0;
        try {
            ZipFile zipFile = new ZipFile(new File(fileStorePath + fileName + ".zip"));
            Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zipFile.entries();
            OutputStream os = httpServletResponse.getOutputStream();
            InputStream is = null;

            is = zipFile.getInputStream(enumeration.nextElement());

            while ((temp = is.read(bytes)) != -1) {
                os.write(bytes, 0, temp);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
