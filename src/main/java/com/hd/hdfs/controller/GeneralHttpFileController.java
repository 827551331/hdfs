package com.hd.hdfs.controller;

import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import com.hd.hdfs.service.GeneralFileHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/http/general")
public class GeneralHttpFileController {

    @Autowired
    private DefaultFileAdapter defaultFileAdapter;

    @Autowired
    private GeneralFileHandleService generalFileHandleServiceImpl;

    /**
     * 批量文件上传
     *
     * @param files
     * @return
     */
    @PostMapping("/uploadFiles")
    public List<FileInfo> uploadFile(@RequestParam("file") MultipartFile[] files) {
        return generalFileHandleServiceImpl.uploadFile(files);
    }

    /**
     * 文件下载
     *
     * @param fileName
     */
    @GetMapping("/download")
    public void downLoadFile(@RequestParam String fileName) {
        defaultFileAdapter.downloadFile(fileName);
    }

    /**
     * 文件预览
     *
     * @param fileName
     */
    @GetMapping("/load")
    public void LoadFile(@RequestParam String fileName) {
        defaultFileAdapter.loadFile(fileName);
    }
}
