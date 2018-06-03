package com.tchoko.springboot.coffeshopapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.tchoko.springboot.coffeshopapp.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
