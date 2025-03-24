package com.project.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class WebMVCConfig {

    public PageableHandlerMethodArgumentResolverCustomizer customizePageable() {
        return resolver -> {
            resolver.setMaxPageSize(10000); // 👈 최대 페이지 크기 설정
            resolver.setOneIndexedParameters(true); // 👈 page=1부터 시작하고 싶으면 true
        };
    }

}
