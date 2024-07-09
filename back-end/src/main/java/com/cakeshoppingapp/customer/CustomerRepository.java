package com.cakeshoppingapp.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Optional<Customer> findByUsername(String username);

	public Optional<Customer> findByEmail(String email);

	public Optional<Customer> findByPhone(String phone);
}
