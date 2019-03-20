package com.hd.hdfs.Controller;

import com.hd.hdfs.hdfile.DefaultFileAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/http")
public class HttpFileController {

    @Autowired
    private DefaultFileAdapter defaultFileAdapter;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return defaultFileAdapter.saveFile(file);
    }

    @GetMapping("/download")
    public void downLoadFile(@RequestParam String fileName, HttpServletResponse httpServletResponse) {
        defaultFileAdapter.downloadFile(fileName, httpServletResponse);
    }
}
