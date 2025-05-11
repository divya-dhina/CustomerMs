package com.cloudkitchen.customer_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-ms")
public interface OrderClient {

    @GetMapping("/api/orders/customer/{customerId}")
    List<Object> getOrdersByCustomerId(@PathVariable("customerId") Long customerId);
}