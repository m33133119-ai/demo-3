package com.example.demo_3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    public String analyzePhotoForChibi(MultipartFile file) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        String mimeType = file.getContentType();

        String promptText = "請分析這張照片中人物的特徵（性別、髮型、髮色、服裝款式、配件等）。然後，請以這些特徵為基礎，幫我寫出一段約 50 字的英文 Prompt，用來輸入給 3D Q版公仔生成器。請直接回傳英文 Prompt 即可。";

        Map<String, Object> textPart = Map.of("text", promptText);
        Map<String, Object> inlineData = Map.of("mime_type", mimeType, "data", base64Image);
        Map<String, Object> imagePart = Map.of("inline_data", inlineData);
        Map<String, Object> content = Map.of("parts", List.of(textPart, imagePart));
        Map<String, Object> requestBody = Map.of("contents", List.of(content));

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=" + apiKey;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            return "呼叫 Gemini 發生錯誤：" + e.getMessage();
        }
    }
}
