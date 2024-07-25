package com.amigoscode.customer;

import com.amigoscode.amqp.RabbitMQMessageProducer;
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
	private final RabbitMQMessageProducer rabbitMQMessageProducer;

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
			if (fraudCheckResponse == null) {
				throw new IllegalStateException("Fraud check response is null");
			}

			if (fraudCheckResponse.isFraudster()) {
				throw new IllegalStateException("Customer flagged as fraudulent during registration");
			}
		} catch (Exception e) {
			log.error("Exception during fraud check: ", e);
		}
		NotificationRequest notificationRequest = new NotificationRequest(
						customer.getId(),
						customer.getEmail(),
						String.format("Hi %s, welcome to Amigoscode...", customer.getFirstName())
				);
		rabbitMQMessageProducer.publish(
				notificationRequest,
				"internal.exchange",
				"internal.notification.routing-key"
		);
	}
}
