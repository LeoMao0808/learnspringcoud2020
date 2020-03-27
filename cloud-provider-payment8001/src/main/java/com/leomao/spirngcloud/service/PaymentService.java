package com.leomao.spirngcloud.service;

import com.leomao.springcloud.entity.Payment;

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(Long id);
}
