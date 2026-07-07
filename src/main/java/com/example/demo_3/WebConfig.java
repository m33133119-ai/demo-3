package com.example.demo_3; // 確保這裡跟你的實際狀況一樣

import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用 Java NIO 原生方法，自動將相對路徑轉換為完美的本機 URI 格式 (file:///C:/...)
        String externalImagesPath = Paths.get("external-images").toAbsolutePath().toUri().toString();
        
        System.out.println("==== 📁 外部圖片通道已開啟！實體路徑: " + externalImagesPath + " ====");

        registry.addResourceHandler("/external-images/**")
                .addResourceLocations(externalImagesPath);
    }
}
