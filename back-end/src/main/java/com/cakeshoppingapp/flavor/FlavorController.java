package com.cakeshoppingapp.flavor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

import jakarta.validation.Valid;

@RestController
public class FlavorController {

	/*
	 * A good practice is letting the controller catching exception and wrap it into
	 * the result object so the client know what's wrong. Update : I think we should
	 * use a different way to catch exception : A better way is making the
	 * Controller cleaner and more readable, by making it exceptions agnostic. and
	 * letting the service layer decide what to do.
	 */

	private final FlavorService flavorService;

	public FlavorController(FlavorService flavorService) {
		this.flavorService = flavorService;
	}

	@GetMapping(value = { "/flavors" })
	public Result findAllFlavors() {
		return new Result(true, StatusCode.SUCCESS, "All Flavors Found!", flavorService.findAll());
	}

	@PostMapping(value = { "/flavors" })
	public Result saveFlavor(@Valid @RequestBody FlavorDTO flavor) {
		return new Result(true, StatusCode.SUCCESS, "Flavor Saved Successfully!", flavorService.save(flavor));
	}

	@GetMapping(path = { "/flavors" }, params = { "name" })
	public Result findByName(@RequestParam("name") String name) {
		return new Result(true, StatusCode.SUCCESS, "Flavor With The Requested Name Is Found!",
				flavorService.findByName(name));
	}

	@GetMapping(value = { "/flavors/{id}" })
	public Result findById(@PathVariable("id") Long id) {
		return new Result(true, StatusCode.SUCCESS, "Flavor With The Requested Name Is Found!",
				flavorService.findById(id));
	}

	@PutMapping(value = { "/flavors/{id}" })
	public Result update(@PathVariable("id") Long id, @Valid @RequestBody FlavorDTO flavorDto) {
		return new Result(true, StatusCode.SUCCESS, "Flavor Updated Successfully!",
				flavorService.update(id, flavorDto));
	}

	@DeleteMapping(value = { "/flavors/{id}" })
	public Result delete(@PathVariable("id") Long id) {
		flavorService.deleteById(id);
		return new Result(true, StatusCode.SUCCESS, "Flavor Deleted Successfully!", null);
	}

}
