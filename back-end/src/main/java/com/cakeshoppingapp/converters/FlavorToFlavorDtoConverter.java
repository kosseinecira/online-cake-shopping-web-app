package com.cakeshoppingapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.flavor.Flavor;

@Component
public class FlavorToFlavorDtoConverter implements Converter<Flavor, FlavorDTO>{

	@Override
	public FlavorDTO convert(Flavor source) {
		return new FlavorDTO(source.getId(), source.getTitle(), source.getDescription());
	}

}
