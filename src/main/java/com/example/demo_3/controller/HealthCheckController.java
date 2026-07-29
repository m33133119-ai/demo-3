package com.example.demo_3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    /**
     * 健康檢查端點
     * 專門供 UptimeRobot 或雲端監控工具定期敲門（Ping）使用
     */
    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
