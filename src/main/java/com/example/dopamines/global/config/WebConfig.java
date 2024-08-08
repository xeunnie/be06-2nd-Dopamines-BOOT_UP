package com.example.dopamines.global.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500") // 프론트엔드가 호스팅되는 도메인
                        .allowedOrigins("http://3.36.126.48:3000")   //Todo
                        .allowedOrigins("http://localhost:8082")  // 허용할 출처를 명시
                        .allowedOrigins("http://localhost:3001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
