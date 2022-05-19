package com.example.crudreservations.controllers;

import com.example.crudreservations.models.Customer;
import com.example.crudreservations.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody String name) {
        Customer customer = new Customer();
        customer.setName(name);
        return customerRepository.save(customer);
    }

    @GetMapping(value = "/customers/{id}")
    public Optional<Customer> getCase(@PathVariable(value = "id") int id) {
        return customerRepository.findById(id);
    }
}
