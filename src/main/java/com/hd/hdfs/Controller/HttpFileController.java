package com.hd.hdfs.Controller;

import com.hd.hdfs.hdfile.DefaultStoreFileAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/http")
public class HttpFileController {

    @Autowired
    private DefaultStoreFileAdapter defaultStoreFileAdapter;

    @PostMapping("/upload")
    public String storeFile(ServletRequest servletRequest, HttpServletRequest httpServletRequest) {
        try {
            servletRequest.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
