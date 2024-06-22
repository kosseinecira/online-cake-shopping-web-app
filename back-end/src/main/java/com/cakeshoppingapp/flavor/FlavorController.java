package com.cakeshoppingapp.flavor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cakeshoppingapp.converters.FlavorDtoToFlavor;
import com.cakeshoppingapp.converters.FlavorToFlavorDtoConverter;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

import jakarta.validation.Valid;

@RestController
public class FlavorController {

	/*
	 * A good practice is letting the controller catching exception and wrap it into
	 * the result object so the client know what's wrong
	 */

	@Autowired
	FlavorService flavorService;
	@Autowired
	FlavorToFlavorDtoConverter flavorToFlavorDtoConverter;
	@Autowired
	FlavorDtoToFlavor flavorDtoToFlavor;

	@GetMapping("/flavors")
	public Result findAllFlavors() {
		List<Flavor> list = flavorService.findAll();
		List<FlavorDTO> data = list.stream().map((flavor) -> flavorToFlavorDtoConverter.convert(flavor)).toList();
		return new Result(true, StatusCode.SUCCESS, "All Flavors Found!", data);

	}

	@PostMapping("/flavors")
	public Result saveFlavor(@Valid @RequestBody FlavorDTO flavor) {
		// In case we want to use an Id generation Algorithm: example : SnowFlake
		// algorithm
		// We need to convent the DTO to Model object and switch it back in the result.
		Flavor savedFlavor = flavorService.save(flavorDtoToFlavor.convert(flavor));
		return new Result(true, StatusCode.SUCCESS, "Flavor Saved Successfully!",
				flavorToFlavorDtoConverter.convert(savedFlavor));
	}

	@GetMapping(path = { "/flavors" }, params = { "title" })
	public Result findByTitle(@RequestParam("title") String title) {
		Flavor flavor = flavorService.findByTitle(title);
		FlavorDTO data = flavorToFlavorDtoConverter.convert(flavor);
		return new Result(true, StatusCode.SUCCESS, "Flavor With The Requested Title Is Found!", data);
	}

	@GetMapping("/flavors/{id}")
	public Result findById(@PathVariable("id") Long id) {
		Flavor flavor = flavorService.findById(id);
		FlavorDTO data = flavorToFlavorDtoConverter.convert(flavor);
		return new Result(true, StatusCode.SUCCESS, "Flavor With The Requested Title Is Found!", data);
	}

	@PutMapping("/flavors/{id}")
	public Result update(@PathVariable("id") Long id, @Valid @RequestBody FlavorDTO flavorDto) {
		Flavor updatedFlavor = flavorService.update(id, flavorDtoToFlavor.convert(flavorDto));
		FlavorDTO data = flavorToFlavorDtoConverter.convert(updatedFlavor);
		return new Result(true, StatusCode.SUCCESS, "Flavor Updated Successfully!", data);
	}

	@DeleteMapping("/flavors/{id}")
	public Result delete(@PathVariable("id") Long id) {
		flavorService.deleteById(id);
		return new Result(true, StatusCode.SUCCESS, "Flavor Deleted Successfully!", null);
	}

}
