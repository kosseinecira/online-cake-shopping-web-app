package com.cakeshoppingapp.dtoes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthenticationDTO(
		@JsonProperty(access = Access.READ_ONLY) Long id,
		@NotEmpty(message = "The Username cannot be empty") 
		@Size(min = 5, max = 20, message = "The username must be between 5 and 20 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "The username must contain only letters and numbers") String username,

		@NotEmpty @Size(min = 3, max = 20, message = "The email must be between 10 and 30 characters") String email,

		@JsonProperty(access = Access.WRITE_ONLY) @NotEmpty(message = "The password cannot be empty") @Size(min = 8, max = 16, message = "The password must be between 8 and 16 characters") String password,

		@JsonProperty(access = Access.READ_ONLY) boolean blocked,
		@JsonProperty(access = Access.READ_ONLY) String role) {

}

/**/
