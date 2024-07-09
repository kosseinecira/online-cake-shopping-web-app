package com.cakeshoppingapp.flavor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cakeshoppingapp.converters.flavor.FlavorDtoToFlavorConverter;
import com.cakeshoppingapp.converters.flavor.FlavorToFlavorDtoConverter;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FlavorService {

	private final FlavorRepository flavorRepository;

	private final FlavorToFlavorDtoConverter flavorToFlavorDtoConverter;

	private final FlavorDtoToFlavorConverter flavorDtoToFlavorConverter;

	public FlavorService(FlavorRepository flavorRepository, FlavorToFlavorDtoConverter flavorToFlavorDtoConverter,
			FlavorDtoToFlavorConverter flavorDtoToFlavorConverter) {
		this.flavorRepository = flavorRepository;
		this.flavorToFlavorDtoConverter = flavorToFlavorDtoConverter;
		this.flavorDtoToFlavorConverter = flavorDtoToFlavorConverter;
	}

	// done!
	public FlavorDTO save(FlavorDTO flavorDto) {
		Boolean flavorExist = flavorRepository.findByName(flavorDto.name()).isPresent();
		if (flavorExist)
			throw new SomethingAlreadyExistException("Flavor With Name: " + flavorDto.name());
		// From DTO to Entity
		Flavor flavorEntity = flavorDtoToFlavorConverter.convert(flavorDto);
		Flavor savedFlavor = flavorRepository.save(flavorEntity);
		// From Entity to DTO
		FlavorDTO returnedFlavorDTO = flavorToFlavorDtoConverter.convert(savedFlavor);
		return returnedFlavorDTO;

	}

	// done!
	public FlavorDTO findByName(String name) {
		Flavor flavor = flavorRepository.findByName(name)
				.orElseThrow(() -> new SomethingNotFoundException("Flavor With Name: " + name));
		return flavorToFlavorDtoConverter.convert(flavor);
	}

	// done!
	public List<FlavorDTO> findAll() {
		return flavorRepository.findAll().stream().filter((cake) -> cake != null)
				.map((flavor) -> flavorToFlavorDtoConverter.convert(flavor)).toList();
	}

	// done!
	public FlavorDTO findById(Long id) {
		Optional<Flavor> flavor = flavorRepository.findById(id);
		Boolean flavorExist = flavor.isPresent();
		if (!flavorExist)
			throw new SomethingNotFoundException("Flavor With Id: " + id);
		return flavorToFlavorDtoConverter.convert(flavor.get());
	}

	// done!
	public FlavorDTO update(Long id, FlavorDTO flavorDtoUpdates) {
		return flavorRepository.findById(id).map(updated -> {
			updated.setName(flavorDtoUpdates.name());
			updated.setDescription(flavorDtoUpdates.description());
			return flavorToFlavorDtoConverter.convert(flavorRepository.save(updated));
		}).orElseThrow(() -> new SomethingNotFoundException("Flavor With Id: " + id));
	}

	public void deleteById(Long id) {
		Optional<Flavor> flavorOptional = flavorRepository.findById(id);
		if (!flavorOptional.isPresent())
			throw new SomethingNotFoundException("Flavor With Id: " + id);
		flavorRepository.deleteById(id);
	}

}
