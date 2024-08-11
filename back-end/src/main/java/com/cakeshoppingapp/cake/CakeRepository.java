package com.cakeshoppingapp.cake;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

	public Optional<Cake> findByName(String name);

	public List<Cake> findByFlavorId(Long flavorId);

	public List<Cake> findByCategoryId(Long categoryId);

	// @Query("select c from Cake c where c.catego = ?1 and c.flavor_id = ?2")
	public List<Cake> findByCategoryIdAndFlavorId(Long categoryId, Long flavorId);
}
