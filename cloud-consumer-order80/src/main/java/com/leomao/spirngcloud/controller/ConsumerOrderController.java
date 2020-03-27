package com.leomao.spirngcloud.controller;

import com.leomao.spirngcloud.lb.LoadBalancer;
import com.leomao.springcloud.entity.CommonResult;
import com.leomao.springcloud.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consumer/payment")
public class ConsumerOrderController {
    /**
     * 单机版这样写可以,集群版要使用暴露出来的服务名  eureka中可以查看服务名,也就是yml文件中配置的项目名
     * 我们这里使用的是restTemplate  需要开启  restTemplate的负载均衡  在restTemplate的bean位置上加入
     **/
//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/create")
    public CommonResult<Payment> createcreate(Payment p){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",p,CommonResult.class);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") String id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/getEntity/{id}")
    public CommonResult<Payment> getPaymentEntity(@PathVariable("id") String id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        } else {
            return new CommonResult<>(500,"操作失败");
        }
    }

    /**
     * 在这边我为了以上程序的正常执行：把自定义接口注释掉，不用自定义负载均衡算法，若想再次启动
     * 请操作一下步骤：
     *          1.注释掉@LoadBalanced（在config下面），放开下方注释，同时会导致上方不可用，因为找不到具体服务
     */

    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances ==null || instances.size()<=0){
            return null;
        }
        //*传入自己的*//*
        ServiceInstance serviceInstance = loadBalancer.instance(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

}
