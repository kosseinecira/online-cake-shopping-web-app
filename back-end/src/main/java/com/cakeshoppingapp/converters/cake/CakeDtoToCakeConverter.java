package com.cakeshoppingapp.converters.cake;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.cake.Cake;
import com.cakeshoppingapp.dtoes.CakeDTO;

@Component
public class CakeDtoToCakeConverter implements Converter<CakeDTO, Cake> {

	@Override
	public Cake convert(CakeDTO source) {
		Cake cake = new Cake();
		cake.setName(source.name());
		cake.setPrice(source.price());
		cake.setDiscount(source.discount());
		cake.setDiameter(source.diameter());
		cake.setHeight(source.height());
		cake.setWeight(source.weight());
		cake.setNetQuantity(source.netQuantity());
		cake.setItAllergen(source.isItAllergen());
		cake.setIngredients(source.ingredients());
		cake.setDeliveryInformation(source.deliveryInformation());
		cake.setDescription(source.description());
		cake.setNoteDescription(source.noteDescription());
		cake.setMessageOnCake(source.messageOnCake());
		cake.setCakeImages(source.cakeImages());
		return cake;
	}

}
