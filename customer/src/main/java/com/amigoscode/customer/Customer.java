package com.amigoscode.customer;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Customer {

	@Id
	@SequenceGenerator(
			name = "customer_id-sequence",
			sequenceName = "customer_id_sequence")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "customer_id_sequence")
	private Integer id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String email;
}
