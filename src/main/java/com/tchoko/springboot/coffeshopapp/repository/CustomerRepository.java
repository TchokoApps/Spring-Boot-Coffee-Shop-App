package com.tchoko.springboot.coffeshopapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.tchoko.springboot.coffeshopapp.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
