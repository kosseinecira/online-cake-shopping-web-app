package com.cakeshoppingapp.customer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.dtoes.AuthenticationDTO;
import com.cakeshoppingapp.dtoes.CustomerDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

import jakarta.validation.Valid;

@RestController
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/users")
	public Result addUser(@Valid @RequestBody AuthenticationDTO customerDTO) {
		AuthenticationDTO customer = customerService.save(customerDTO);
		return new Result(true, StatusCode.SUCCESS, "Customer Added Successfully!", customer);
	}

	@GetMapping("/users")
	public Result getAllUsers() {
		return new Result(true, StatusCode.SUCCESS, "Customers Fetched Successfully!", customerService.findAll());
	}

	@GetMapping("/users/{id}")
	public Result getUserById(@PathVariable Long id) {
		return new Result(true, StatusCode.SUCCESS, "Customer Fetched Successfully!", customerService.findById(id));
	}

	@GetMapping(value = "/users", params = { "username" })
	public Result getUserByUsername(@RequestParam(name = "username", required = true) String username) {
		return new Result(true, StatusCode.SUCCESS, "User Found!", customerService.findByUsername(username));
	}

	@GetMapping(value = "/users", params = { "email" })
	public Result getUserByEmail(@RequestParam(name = "email", required = true) String email) {
		return new Result(true, StatusCode.SUCCESS, "User Found!", customerService.findByEmail(email));
	}

	@GetMapping(value = "/users", params = { "phone" })
	public Result getUserByPhone(@RequestParam(name = "phone", required = true) String phone) {
		return new Result(true, StatusCode.SUCCESS, "User Found!", customerService.findByPhone(phone));
	}

	@PutMapping(value = "/users/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public Result updateUser(@PathVariable Long userId, @RequestPart CustomerDTO updateCustomerDTO,
			@RequestPart MultipartFile image) {

		return new Result(true, StatusCode.SUCCESS, "Customer Updated Successfully!",
				customerService.update(userId, updateCustomerDTO, image));

	}

	// visit to block, revisit to unblock and vice versa
	@PatchMapping(value = "/users/{id}")
	public Result blockAnUnblockUser(@PathVariable Long id) {
		customerService.blockAndUnblockCustomerById(id);
		return new Result(true, StatusCode.SUCCESS, "Customer Blocked | Unblocked Successfully");
	}

	@DeleteMapping("/users/{id}")
	public Result deleteUser(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return new Result(true, StatusCode.SUCCESS, "Customer Deleted Successfully");
	}
}
