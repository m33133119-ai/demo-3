package com.example.demo_3.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo_3.service.GeminiService;

@RestController
@RequestMapping("/api")
public class FileController {

   
    @Autowired
    private GeminiService geminiService;

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("請選擇檔案！");
        }

        try {
        	
                // 加入追蹤器 1
                System.out.println("==== 1. 成功收到照片，準備連線 Gemini ====");

                // 1. ⚡ 率先將照片資料流送給 Gemini 進行視覺特徵分析
                String aiRawResponse = geminiService.analyzePhotoForChibi(file);

                // 加入追蹤器 2
                System.out.println("==== 2. 成功收到 Gemini 總部回傳的報告 ====");

                // 2. 將照片存檔到硬碟備份（保留原有功能）
                File directory = new File(UPLOAD_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + extension;

                File dest = new File(UPLOAD_DIR + newFileName);
                file.transferTo(dest);

                // 3. 🎉 成功！將 Gemini 回傳的 JSON 數據直接丟回給前端網頁
                return ResponseEntity.ok(aiRawResponse);

            } catch (Exception e) {
                return ResponseEntity.internalServerError().body("AI 連線或存檔失敗：" + e.getMessage());
            }
        
    }
       
}


