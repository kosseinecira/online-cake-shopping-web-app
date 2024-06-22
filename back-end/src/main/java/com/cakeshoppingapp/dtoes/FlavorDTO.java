package com.cakeshoppingapp.dtoes;

import jakarta.validation.constraints.NotBlank;

public record FlavorDTO(Long id, @NotBlank(message = "The Title Field Can Not Be Empty") String title,
		String description) {

}
