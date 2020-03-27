package com.leomao.springcloud.service;

import com.leomao.springcloud.entity.CommonResult;
import com.leomao.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> get(@PathVariable("id") String id);

    @GetMapping("/payment/testTimeOut")
    String testTimeOut();

}
