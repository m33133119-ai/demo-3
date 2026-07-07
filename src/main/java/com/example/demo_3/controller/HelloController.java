package com.example.demo_3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/test")
    public String testEngine() {
        return "引擎發動成功！3D 公仔伺服器已上線！";
    }
}