package com.cakeshoppingapp.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	public Optional<Category> findByName(String name);
}
