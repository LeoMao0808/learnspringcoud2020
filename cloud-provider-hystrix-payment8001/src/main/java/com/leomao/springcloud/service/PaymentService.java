package com.leomao.springcloud.service;
import	java.util.concurrent.TimeUnit;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {

    /**
     * 正常访问
     *
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_OK,id:" + id + "\t" + "O(∩_∩)O哈哈~";
    }

    /**
     * 超时访问
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 5;
        try { TimeUnit.SECONDS.sleep(timeNumber); } catch (InterruptedException e) { e.printStackTrace();}
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t" +
                "O(∩_∩)O哈哈~  耗时(秒)"+timeNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池:" + Thread.currentThread().getName() + " 8001系统繁忙请稍后再试！！,id:" + id + "\t"+"我哭了！！";
    }

    //===服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //这些配置项在HystrixCommandProperties类中可以找到
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启熔断器 默认开启
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数 默认20
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口 /时间范文 默认 5000
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),//请求错误率 默认50%
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id不能为负数");
        }
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号:"+uuid;
    }
    public  String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数,稍后重试 ┭┮﹏┭┮ ~~ id:" + id;
    }
}
