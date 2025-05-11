package com.cloudkitchen.customer_ms.repository;

import com.cloudkitchen.customer_ms.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
}

