package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;//接收和处理通过 HTTP 上传的文件

import java.io.File;

@Service
public class UploadService {

    public String saveFile(MultipartFile file,String name) throws Exception {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 将上传的文件重命名为name
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = name + extension;
        String filePath = uploadDir + newFileName;

        file.transferTo(new File(filePath));

        return filePath;
    }
}
