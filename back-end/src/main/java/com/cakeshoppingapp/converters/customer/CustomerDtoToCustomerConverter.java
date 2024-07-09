package com.cakeshoppingapp.converters.customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.customer.Customer;
import com.cakeshoppingapp.dtoes.CustomerDTO;

@Component
public class CustomerDtoToCustomerConverter implements Converter<CustomerDTO, Customer> {

	@Override
	public Customer convert(CustomerDTO source) {
		return new Customer(source.id(), source.username(), source.email(), source.password(), source.firstName(),
				source.lastName(), source.phone(), source.emailVerified(), source.phoneVerified(), source.imagePath(),
				source.address());
	}
}
