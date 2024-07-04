package com.cakeshoppingapp.converters.flavor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.flavor.Flavor;

@Component
public class FlavorDtoToFlavorConverter implements Converter<FlavorDTO, Flavor> {

	@Override
	public Flavor convert(FlavorDTO source) {
		return new Flavor(source.name(), source.description());
	}
}
