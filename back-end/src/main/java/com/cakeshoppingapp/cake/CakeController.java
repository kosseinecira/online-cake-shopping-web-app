package com.cakeshoppingapp.cake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cakeshoppingapp.converters.cake.CakeDtoToCakeConverter;
import com.cakeshoppingapp.converters.cake.CakeToCakeDtoConverter;
import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.dtoes.CakeMultipleFileDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

import jakarta.validation.Valid;

@RestController
public class CakeController {
	@Autowired
	CakeService cakeService;

	@Autowired
	CakeDtoToCakeConverter cakeDtoToCakeConverter;

	@Autowired
	CakeToCakeDtoConverter cakeToCakeDtoConverter;

	@PostMapping(value = "/flavors/{flavorId}/cakes", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Result save(@Valid CakeMultipleFileDTO cakeMultipleFileDTO, @PathVariable("flavorId") Long flavorId) {
		return new Result(true, StatusCode.SUCCESS, "Cake Saved Successfully!",
				cakeService.save(flavorId, cakeMultipleFileDTO));
	}

	@GetMapping("/flavors/{flavorId}/cakes/{cakeId}")
	public Result findById(@PathVariable Long cakeId) {
		return new Result(true, StatusCode.SUCCESS, "Cake Found!", cakeService.findById(cakeId));
	}

	@PutMapping("/flavors/{flavorId}/cakes/{cakeId}")
	public Result updateCake(@PathVariable("flavorId") Long flavorId, @PathVariable("cakeId") Long cakeId,
			@Valid @RequestBody CakeDTO cakeDto) {
		return new Result(true, StatusCode.SUCCESS, "Cake Updated Successfully!",
				cakeService.update(flavorId, cakeId, cakeDto));
	}

	@DeleteMapping("/flavors/{flavorId}/cakes/{cakeId}")
	public Result delete(@PathVariable("cakeId") Long cakeId) {
		cakeService.deleteById(cakeId);
		return new Result(true, StatusCode.SUCCESS, "Cake Deleted Successfully!", null);
	}

	@GetMapping("/cakes")
	public Result findAll() {
		return new Result(true, StatusCode.SUCCESS, "All Cakes Found!", cakeService.findAll());
	}

	@GetMapping(value = "/cakes", params = { "name" })
	public Result findByName(@RequestParam String name) {
		return new Result(true, StatusCode.SUCCESS, "Cake Found!", cakeService.findByName(name));
	}

	@GetMapping(value = "flavors/{flavorId}/cakes")
	public Result findAllByFlavorId(@PathVariable("flavorId") Long flavorId) {
		return new Result(true, StatusCode.SUCCESS, "Cakes Found Under Flavor " + flavorId,
				cakeService.findByFlavorId(flavorId));
	}
}
