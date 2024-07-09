package com.cakeshoppingapp.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.converters.customer.AuthenticationDtoToCustomerConverter;
import com.cakeshoppingapp.converters.customer.CustomerDtoToCustomerConverter;
import com.cakeshoppingapp.converters.customer.CustomerToAuthenticationDtoConverter;
import com.cakeshoppingapp.converters.customer.CustomerToCustomerDtoConverter;
import com.cakeshoppingapp.dtoes.AuthenticationDTO;
import com.cakeshoppingapp.dtoes.CustomerDTO;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.cakeshoppingapp.utils.FileUploadUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerToAuthenticationDtoConverter customerToAuthenticationDtoConverter;
	private final AuthenticationDtoToCustomerConverter authenticationDtoToCustomerConverter;

	private final CustomerToCustomerDtoConverter customerToCustomerDtoConverter;
	private final CustomerDtoToCustomerConverter customerDtoToCustomerConverter;

	public CustomerService(CustomerRepository customerRepository,
			CustomerToAuthenticationDtoConverter customerToCustomerDtoConverter,
			AuthenticationDtoToCustomerConverter authenticationDtoToCustomerConverter,
			CustomerToCustomerDtoConverter customerToCustomerUpdateDto,
			CustomerDtoToCustomerConverter customerUpdateDtoToCustomer) {
		this.customerRepository = customerRepository;
		this.customerToAuthenticationDtoConverter = customerToCustomerDtoConverter;
		this.authenticationDtoToCustomerConverter = authenticationDtoToCustomerConverter;
		this.customerToCustomerDtoConverter = customerToCustomerUpdateDto;
		this.customerDtoToCustomerConverter = customerUpdateDtoToCustomer;
	}

	public AuthenticationDTO save(AuthenticationDTO authenticationDTO) {
		checkCustomerExistance(authenticationDTO.username(), authenticationDTO.email());
		Customer customer = authenticationDtoToCustomerConverter.convert(authenticationDTO);
		return customerToAuthenticationDtoConverter.convert(customerRepository.save(customer));
	}

	public List<CustomerDTO> findAll() {
		return customerRepository.findAll().stream().map(customer -> customerToCustomerDtoConverter.convert(customer))
				.toList();
	}

	public CustomerDTO findById(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Id: " + id));
		return customerToCustomerDtoConverter.convert(customer);
	}

	public CustomerDTO findByUsername(String username) {
		Customer customer = customerRepository.findByUsername(username)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Username: " + username));
		return customerToCustomerDtoConverter.convert(customer);
	}

	public CustomerDTO findByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Email: " + email));
		return customerToCustomerDtoConverter.convert(customer);
	}

	public CustomerDTO findByPhone(String phone) {
		Customer customer = customerRepository.findByPhone(phone)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Phone Number: " + phone));
		return customerToCustomerDtoConverter.convert(customer);
	}

	public CustomerDTO update(Long id, CustomerDTO customerdtoUpdates, MultipartFile image) {

		Customer currentCustomer = customerRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Id: " + id));
		// email and user name comparison logic will be added later
		Customer customer = customerDtoToCustomerConverter.convert(customerdtoUpdates);

		if (image != null)
			customer.setImagePath(handleImageSaving(id, image));
		// email and phone verification logic will be added later
		//
		return customerToCustomerDtoConverter.convert(customerRepository.save(currentCustomer));

	}

	public void deleteCustomer(Long id) {
		customerRepository.findById(id).orElseThrow(() -> new SomethingNotFoundException("Customer With Id: " + id));
		customerRepository.deleteById(id);
	}

	private String handleImageSaving(Long customerId, MultipartFile image) {
		String imagesPath = "resources\\static\\images\\customers_profile_images\\" + customerId;
		return FileUploadUtil.saveImageToPath(image, imagesPath)[1];
	}

	private void checkCustomerExistance(String username, String email) {
		boolean usernameExist = customerRepository.findByUsername(username).isPresent();
		boolean emailExist = customerRepository.findByUsername(email).isPresent();
		if (usernameExist) {
			throw new SomethingAlreadyExistException("Customer With Username: " + username);
		}
		if (emailExist) {
			throw new SomethingAlreadyExistException("Customer With Email: " + email);
		}

	}

	public void blockAndUnblockCustomerById(Long id) {
		Customer customerWillBeBlocked = customerRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Customer With Id: " + id));
		customerWillBeBlocked.setBlocked(!customerWillBeBlocked.isBlocked());
		customerRepository.save(customerWillBeBlocked);
	}

}
