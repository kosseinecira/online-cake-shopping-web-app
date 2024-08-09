package com.cakeshoppingapp.converters.customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.customer.Customer;
import com.cakeshoppingapp.dtoes.AuthenticationDTO;

@Component
public class CustomerToAuthenticationDtoConverter implements Converter<Customer, AuthenticationDTO> {

	@Override
	public AuthenticationDTO convert(Customer source) {
		return new AuthenticationDTO(source.getId(), source.getEmail(), source.getPassword(), source.isBlocked(),
				source.getRole());
	}

}
