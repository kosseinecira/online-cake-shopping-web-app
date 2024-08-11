package com.cakeshoppingapp.dtoes;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CakeMultipleFileDTO(@NotBlank String name, double price, @PositiveOrZero double discount,
		@Positive double diameter, @Positive double height, @Positive double weight, @PositiveOrZero int netQuantity,
		boolean isItAllergen, @NotEmpty String ingredients, @NotEmpty String deliveryInformation, String description,
		String noteDescription, String messageOnCake, @JsonProperty List<MultipartFile> cakeImages) {

}
