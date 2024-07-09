package com.cakeshoppingapp.converters.customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.customer.Customer;
import com.cakeshoppingapp.dtoes.AuthenticationDTO;

@Component
public class AuthenticationDtoToCustomerConverter implements Converter<AuthenticationDTO, Customer> {

	@Override
	public Customer convert(AuthenticationDTO source) {
		return new Customer(source.id(), source.username(), source.email(), source.password(), source.role());
	}
}
