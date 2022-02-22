package com.uc_mobileapps.examples.rest.dao;

import com.uc_mobileapps.examples.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findById(long id);

    Page<Customer> findAll(Pageable pageable);

    long count();
}