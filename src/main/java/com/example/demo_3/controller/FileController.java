package com.example.demo_3.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("請選擇檔案！");
        }
        try {
            // 1. 將照片存檔到硬碟
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            File dest = new File(UPLOAD_DIR + newFileName);
            file.transferTo(dest);

            // 2. 回傳成功訊息（原本這裡回傳的是 Gemini 分析結果）
            return ResponseEntity.ok("上傳成功：" + newFileName);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("存檔失敗：" + e.getMessage());
        }
    }

}


