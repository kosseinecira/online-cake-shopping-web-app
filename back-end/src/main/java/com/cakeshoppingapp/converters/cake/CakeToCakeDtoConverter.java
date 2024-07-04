package com.cakeshoppingapp.converters.cake;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.cake.Cake;
import com.cakeshoppingapp.dtoes.CakeDTO;

@Component
public class CakeToCakeDtoConverter implements Converter<Cake, CakeDTO> {

	@Override
	public CakeDTO convert(Cake source) {
		return new CakeDTO(source.getId(), source.getName(), source.getPrice(), source.getDiscount(),
				source.getDiameter(), source.getHeight(), source.getWeight(), source.getNetQuantity(),
				source.isItAllergen(), source.getIngredients(), source.getDeliveryInformation(),
				source.getDescription(), source.getNoteDescription(), source.getMessageOnCake(), source.getCakeImages(),
				source.getFlavor().getName());
	}

}
