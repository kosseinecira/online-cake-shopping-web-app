package com.cakeshoppingapp.converters.customer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.customer.Customer;
import com.cakeshoppingapp.dtoes.CustomerDTO;

@Component
public class CustomerToCustomerDtoConverter implements Converter<Customer, CustomerDTO> {

	@Override
	public CustomerDTO convert(Customer source) {
		return new CustomerDTO(source.getId(), source.getUsername(), source.getEmail(), source.getPassword(),
				source.getFirstName(), source.getLastName(), source.getPhone(), source.getRole(),
				source.isEmailVerified(), source.isPhoneVerified(), source.isBlocked(),
				source.isAllInformationProvided(), source.getImagePath(), source.getAddress());
	}

}
