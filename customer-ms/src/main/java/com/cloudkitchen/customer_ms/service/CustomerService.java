package com.cloudkitchen.customer_ms.service;

import com.cloudkitchen.customer_ms.model.Customer;
import com.cloudkitchen.customer_ms.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedData) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        existingCustomer.setName(updatedData.getName());
        existingCustomer.setPhone(updatedData.getPhone());
        existingCustomer.setAddress(updatedData.getAddress());
        existingCustomer.setUsername(updatedData.getUsername());
        existingCustomer.setPassword(updatedData.getPassword());
        existingCustomer.setRole(updatedData.getRole());

        return customerRepository.save(existingCustomer);
    }
}

