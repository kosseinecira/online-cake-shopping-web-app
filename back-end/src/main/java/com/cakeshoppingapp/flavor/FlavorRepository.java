package com.cakeshoppingapp.flavor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long> {

	@Query("SELECT f FROM Flavor f where f.title = :title")
	public Optional<Flavor> findByTitle(String title);

}
