package com.cakeshoppingapp.dtoes;

import java.util.List;

import com.cakeshoppingapp.image.CakeImage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CakeDTO(Long id, @NotBlank String name, @Positive double price, @PositiveOrZero double discount,
		@Positive double diameter, @Positive double height, @Positive double weight, @PositiveOrZero int netQuantity,
		boolean isItAllergen, @NotEmpty String ingredients, @NotEmpty String deliveryInformation, String description,
		String noteDescription, String messageOnCake, List<CakeImage> cakeImages, String flavorName) {

}
