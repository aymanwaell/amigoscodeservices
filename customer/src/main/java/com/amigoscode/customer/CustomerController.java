package com.amigoscode.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

	private final CustomerServices customerServices;

	@PostMapping
	public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
	    try {
	        customerServices.registerCustomer(customerRegistrationRequest);
	        log.info("New customer registered: {}", customerRegistrationRequest);
	        return ResponseEntity.ok("Customer registered successfully");
	    } catch (Exception e) {
	        log.error("Error registering customer: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register customer");
	    }
	}
}
