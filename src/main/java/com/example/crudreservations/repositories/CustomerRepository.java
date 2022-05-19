package com.example.crudreservations.repositories;

import com.example.crudreservations.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
