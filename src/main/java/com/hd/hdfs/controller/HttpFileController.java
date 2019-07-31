package com.hd.hdfs.controller;

import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@Deprecated
@RestController
@RequestMapping("/http")
public class HttpFileController {

    @Autowired
    private DefaultFileAdapter defaultFileAdapter;

    /**
     * 单个文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return defaultFileAdapter.saveFile(file);
    }

    /**
     * 批量文件上传
     *
     * @param files
     * @return
     */
    @PostMapping("/uploadFiles")
    public String[] uploadFile(@RequestParam("file") MultipartFile[] files) {
        return defaultFileAdapter.saveFiles(files);
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param httpServletResponse
     */
    @GetMapping("/download")
    public void downLoadFile(@RequestParam String fileName, HttpServletResponse httpServletResponse) {
        defaultFileAdapter.downloadFile(fileName);
    }
}
