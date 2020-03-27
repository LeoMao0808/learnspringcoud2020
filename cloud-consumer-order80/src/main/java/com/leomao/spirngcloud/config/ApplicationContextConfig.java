package com.leomao.spirngcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    /**
     * 注入RestTemplate到spring容器
     * 使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
     * @return RestTemplate
     */
    @Bean
//    @LoadBalanced 手写轮询需要注掉这里
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
