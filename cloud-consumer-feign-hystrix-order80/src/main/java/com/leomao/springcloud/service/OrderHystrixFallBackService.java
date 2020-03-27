package com.leomao.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderHystrixFallBackService  implements OrderHystrixService{

    @Override
    public String paymentInfo_OK(Integer id) {
        return "--- PaymentFallbackService  fall  paymentInfo_OK vack ，/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "--- PaymentFallbackService  fall  paymentInfo_TimeOut， /(ㄒoㄒ)/~~";
    }
}
