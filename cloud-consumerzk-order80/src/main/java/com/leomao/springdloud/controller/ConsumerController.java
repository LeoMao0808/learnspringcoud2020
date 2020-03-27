package com.leomao.springdloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer/payment")
public class ConsumerController {

    public static final String PAYMENT_URL = "http://cloud-provider-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/zk")
    public String getPayment(){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/zk",String.class);
    }


}
