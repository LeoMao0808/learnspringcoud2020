package com.leomao.springcloud.controller;

import com.leomao.springcloud.entity.CommonResult;
import com.leomao.springcloud.entity.Payment;
import com.leomao.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/payment")
@Slf4j
public class PaymentFeginController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id){
        return paymentFeignService.get(id);
    }

    @GetMapping("/testTimeOut")
    public String testTimeOut() {
        return paymentFeignService.testTimeOut();
    }

}
