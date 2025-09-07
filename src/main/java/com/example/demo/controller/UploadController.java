package com.example.demo.controller;

import com.example.demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")

    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name // 提交者姓名
    ) {
        try {
            String filePath = uploadService.saveFile(file,name);

            return "上传成功！文件保存到：" + filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败：" + e.getMessage();
        }
    }
}
