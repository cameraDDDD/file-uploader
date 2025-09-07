package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class DownloadController {

    private final String uploadDir = System.getProperty("user.dir") 
    + "/uploads/";

    @GetMapping("/getAPK")
    public void downloadZip(HttpServletResponse response) {
        String zipFileName = "uploads.zip";

        try {
            // 设置响应头，让浏览器下载文件
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);

            // 创建Zip输出流，写入response的输出流
            try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
                Path sourceDir = Paths.get(uploadDir);

                if (!Files.exists(sourceDir)) {
                    throw new FileNotFoundException("上传目录不存在: " + uploadDir);
                }

                // 遍历uploads目录，逐个文件写入zip
                Files.walk(sourceDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                    String zipEntryName = sourceDir.relativize(path).toString(); // 相对路径
                    try (InputStream fis = Files.newInputStream(path)) {
                        zos.putNextEntry(new ZipEntry(zipEntryName));
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

