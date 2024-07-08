package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServices {

	private final CustomerRepository customerRepository;
	private final FraudClient fraudClient;
	private final NotificationClient notificationClient;

	@Transactional
	public void registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.build();
		customerRepository.saveAndFlush(customer);

		try {
			FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
			if (fraudCheckResponse.isFraudster()) {
				throw new IllegalStateException("Customer flagged as fraudulent during registration");
			}
		} catch (NullPointerException e) {
			log.error("Customer ID was null after saving customer. Fraud check not performed.", e);
		}
		notificationClient.sendNotification(
				new NotificationRequest(
						customer.getId(),
						customer.getEmail(),
						String.format("Hi %s, welcome to Amigoscode...", customer.getFirstName())
				)
		);
	}
}