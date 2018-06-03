package com.tchoko.springboot.coffeshopapp.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tchoko.springboot.coffeshopapp.model.Customer;
import com.tchoko.springboot.coffeshopapp.model.CustomerOrder;
import com.tchoko.springboot.coffeshopapp.model.Product;
import com.tchoko.springboot.coffeshopapp.repository.CustomerRepository;
import com.tchoko.springboot.coffeshopapp.repository.OrderRepository;
import com.tchoko.springboot.coffeshopapp.repository.ProductRepository;

@Controller
public class OrdersController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("orders")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("orders", orderRepository.findAll());
		return "orders";
	}

	@PostMapping("/createorder")
	@ResponseBody
	public String saveOrder(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(value = "productIds[]") Long[] productIds) {

		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customerRepository.save(customer);

		CustomerOrder customerOrder = new CustomerOrder();
		Optional<Customer> customerOptional = customerRepository.findById(customer.getId());
		if (customerOptional.isPresent()) {
			customerOrder.setCustomer(customerOptional.get());
		}

		Set<Product> products = new HashSet<>();

		Arrays.stream(productIds).forEach(id -> {
			Optional<Product> optionalProduct = productRepository.findById(id);
			if (optionalProduct.isPresent()) {
				products.add(optionalProduct.get());
			}
		});

		customerOrder.setProducts(products);

		Double total = 0.0;

		for (Long productId : productIds) {
			Optional<Product> optionalProduct = productRepository.findById(productId);
			if (optionalProduct.isPresent()) {
				total = total + optionalProduct.get().getProductPrice();
			}
		}
		
		customerOrder.setTotal(total);
		orderRepository.save(customerOrder);
		
		return customerOrder.getId().toString();

	}
	
	@PostMapping("/removeorder") 
	@ResponseBody
	public String removeOrder(@RequestParam Long id) {
		orderRepository.deleteById(id);
		return id.toString();
	}
}
