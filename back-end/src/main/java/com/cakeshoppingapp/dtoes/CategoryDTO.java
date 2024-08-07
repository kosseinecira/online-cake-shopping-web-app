package com.cakeshoppingapp.dtoes;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;

public record CategoryDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
		@NotEmpty(message = "Category name cannot be empty!") String name,
		@JsonProperty(access = JsonProperty.Access.READ_ONLY) String categoryImagePath) {

}
