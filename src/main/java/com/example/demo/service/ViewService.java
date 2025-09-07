package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.constant.ClassConstant;

@Service
public class ViewService {
    /**
     * 获取所有文件名
     */
    public List<String> getFilesName() {
        File folder = new File("uploads");
        File[] files = folder.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }
    /**
     * 获取未提交的同学名字
     */
    public List<String> findBitches() {
        List<String> bitches = Arrays.asList(ClassConstant.NAMES);
        File folder = new File("uploads");
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            return new ArrayList<>(bitches); // 如果没有文件，返回所有
        }

        // 提取所有文件名
        List<String> fileNames = Arrays.stream(files)
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());

        // 过滤掉在文件名中出现过的名字然后包装为列表
        return bitches.stream()
                .filter(bitch -> fileNames.stream().noneMatch(name -> name.contains(bitch)))
                .collect(Collectors.toList());
    }
}
