package com.leomao.spirngcloud;

import com.leomao.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)//配置自定义负载规则
public class ConsumerOrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrderMain80.class,args);
    }
}
