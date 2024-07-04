package com.cakeshoppingapp.dtoes;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

public record FlavorDTO(Long id, @NotBlank(message = "The Name Field Can Not Be Empty") String name,
		String description) {

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FlavorDTO flavorDTO = (FlavorDTO) o;
		return Objects.equals(id, flavorDTO.id) && Objects.equals(name, flavorDTO.name)
				&& Objects.equals(description, flavorDTO.description);
	}
}
