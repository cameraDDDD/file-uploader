package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ViewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
public class ViewController {

    @Autowired
    private ViewService viewService;
    @GetMapping("/committed")
    public List<String> admittedList(){
        return viewService.getFilesName();
    }

    @GetMapping("/uncommitted")
    public List<String> getMethodName() {
        return viewService.findBitches();
    }


}
