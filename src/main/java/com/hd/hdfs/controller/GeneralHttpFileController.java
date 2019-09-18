package com.hd.hdfs.controller;

import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import com.hd.hdfs.hdfile.adapter.LoadPicAdapter;
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
    private LoadPicAdapter loadPicAdapter;

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
     * 文件批量下载
     *
     * @param fileNames
     */
    @PostMapping("/batchDownload")
    public void batchDownLoadFile(@RequestBody String[] fileNames) {
        defaultFileAdapter.batchDownloadFile(fileNames);
    }

    /**
     * 图片预览
     *
     * @param fileName
     */
    @GetMapping("/loadPic")
    public void LoadPicFile(@RequestParam String fileName) {
        loadPicAdapter.loadFile(fileName);
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
