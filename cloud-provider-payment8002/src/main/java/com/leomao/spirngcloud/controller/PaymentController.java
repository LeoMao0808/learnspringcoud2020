package com.leomao.spirngcloud.controller;

import com.leomao.spirngcloud.service.PaymentService;
import com.leomao.springcloud.entity.CommonResult;
import com.leomao.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    public String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("***插入结果:"+result);
        if (result > 0 ){
            return new CommonResult(200,"插入成功,服务端口:"+serverPort,result);
        }else{
            return new CommonResult(500,"插入失败,服务端口:"+serverPort,null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult create(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("***查询结果:"+result);
        if (result != null){
            return new CommonResult(200,"查询成功,服务端口:"+serverPort,result);
        }else{
            return new CommonResult(500,"查询失败,服务端口:"+serverPort,null);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping(value = "/testTimeOut")
    public String testTimeOut() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
