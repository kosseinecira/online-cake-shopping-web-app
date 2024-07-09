package com.cakeshoppingapp.dtoes;

import com.cakeshoppingapp.customer.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CustomerDTO(Long id, @JsonProperty(access = Access.READ_ONLY) String username,
		@NotEmpty @Size(min = 3, max = 20, message = "The email must be between 10 and 30 characters") String email,

		@JsonProperty(access = Access.WRITE_ONLY) @NotEmpty(message = "The password cannot be empty") @Size(min = 8, max = 16, message = "The password must be between 8 and 16 characters") String password,

		@NotEmpty @Size(min = 3, max = 20, message = "The first name must be between 3 and 20 characters") String firstName,
		@NotEmpty @Size(min = 10, max = 20, message = "The last name must be between 3 and 20 characters") String lastName,
		@Size(min = 10, max = 10) @Size(min = 10, max = 10, message = "The phone number must be between 10 and 10 degists") String phone,
		@JsonProperty(access = Access.READ_ONLY) String role,
		@JsonProperty(access = Access.READ_ONLY) boolean emailVerified,
		@JsonProperty(access = Access.READ_ONLY) boolean phoneVerified,
		@JsonProperty(access = Access.READ_ONLY) boolean blocked,
		@JsonProperty(access = Access.READ_ONLY) boolean allInformationProvided, String imagePath, Address address) {

//	(Long id, String username, String password, String firstName, String lastName, String email,
//	String phone, String role, String imagePath, Address address)

}
