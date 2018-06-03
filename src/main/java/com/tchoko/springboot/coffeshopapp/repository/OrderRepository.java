package com.tchoko.springboot.coffeshopapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.tchoko.springboot.coffeshopapp.model.CustomerOrder;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long> {

}
