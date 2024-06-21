package com.cakeshoppingapp.flavor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;

import lombok.NoArgsConstructor;

@Service
public class FlavorService {

	@Autowired
	private FlavorRepository flavorRepository;

	public Flavor save(Flavor flavor) {
		Boolean flavorExist = flavorRepository.findByTitle(flavor.getTitle()).isPresent();
		if (flavorExist)
			throw new SomethingAlreadyExistException(flavor.getTitle());
		return flavorRepository.save(flavor);
	}

	public Flavor findByTitle(String title) {
		Flavor flavor = flavorRepository.findByTitle(title).orElseThrow(() -> new SomethingNotFoundException(title));
		return flavor;
	}

	public List<Flavor> findAll() {
		return flavorRepository.findAll();
	}

	public Flavor findById(long id) {
		Optional<Flavor> flavor = flavorRepository.findById(id);
		Boolean flavorExist = flavor.isPresent();
		if (!flavorExist)
			throw new SomethingNotFoundException(id + "");
		return flavor.get();
	}

	public Flavor update(Long id, Flavor flavorUpdates) {
		return flavorRepository.findById(id).map(updated -> {
			updated.setTitle(flavorUpdates.getTitle());
			updated.setDescription(flavorUpdates.getDescription());
			return flavorRepository.save(updated);
		}).orElseThrow(() -> new SomethingNotFoundException(id + ""));
//		Flavor oldFlavor = flavorRepository.findById(id).get();
//		oldFlavor.setTitle(flavorUpdates.getTitle());
//		oldFlavor.setDescription(flavorUpdates.getDescription());
//		Flavor updatedFlavor = flavorRepository.save(oldFlavor);
//		return updatedFlavor;
	}

	public void deleteById(Long id) {
		Optional<Flavor> flavorOptional = flavorRepository.findById(id);
		if(!flavorOptional.isPresent()) throw new SomethingNotFoundException(id+"");
		flavorRepository.deleteById(id);
	}

}
