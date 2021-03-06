package com.hd.hdfs.controller;

import com.hd.hdfs.entity.FileInfo;
import com.hd.hdfs.hdfile.adapter.DefaultFileAdapter;
import com.hd.hdfs.hdfile.adapter.LoadPicAdapter;
import com.hd.hdfs.service.FileHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("generalFileHandleServiceImpl")
    private FileHandleService generalFileHandleServiceImpl;

    @Autowired
    @Qualifier("fixedFileHandleServiceImpl")
    private FileHandleService fixedFileHandleServiceImpl;

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
     * 固定名称文件批量文件上传（后台不修改文件名）
     *
     * @param files
     * @return
     */
    @PostMapping("/uploadFiles/fixedName")
    public List<FileInfo> uploadFileByFixedName(@RequestParam("file") MultipartFile[] files) {
        return fixedFileHandleServiceImpl.uploadFile(files);
    }

    /**
     * 文件下载
     *
     * @param fileName
     */
    @GetMapping("/download")
    public void downLoadFile(@RequestParam String fileName) {
        generalFileHandleServiceImpl.downloadFile(fileName);
    }

    /**
     * 根据 id 文件下载
     *
     * @param id
     */
    @GetMapping("/downloadById")
    public void downLoadFileById(@RequestParam String id) {
        generalFileHandleServiceImpl.downloadFileById(id);
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
