package com.cakeshoppingapp.cake;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;
import com.cakeshoppingapp.utils.Constant;

import jakarta.validation.Valid;

@RestController
public class CakeController {

	private final CakeService cakeService;

	public CakeController(CakeService cakeService) {
		this.cakeService = cakeService;
	}

	@PostMapping(value = "/categories/{categoryId}/flavors/{flavorId}/cakes", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Result save(@Valid @RequestPart("cakeDto") CakeDTO cakeDto,
			@RequestPart(value = "cakeImageList", required = false) List<MultipartFile> cakeImageList,
			@PathVariable("categoryId") Long categoryId, @PathVariable("flavorId") Long flavorId) {
		return new Result(true, StatusCode.SUCCESS, "Cake Saved Successfully!",
				cakeService.save(categoryId, flavorId, cakeDto, cakeImageList));
	}

	@PutMapping("/categories/{categoryId}/flavors/{flavorId}/cakes/{cakeId}")
	public Result updateCake(@PathVariable("categoryId") Long categoryId, @PathVariable("flavorId") Long flavorId,
			@PathVariable("cakeId") Long cakeId, @Valid @RequestPart("cakeDto") CakeDTO cakeDto,
			@RequestPart("cakeImageList") List<MultipartFile> cakeImages) {
		return new Result(true, StatusCode.SUCCESS, "Cake Updated Successfully!",
				cakeService.update(categoryId, flavorId, cakeId, cakeDto, cakeImages));
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

	@GetMapping("/cakes/{cakeId}")
	public Result findById(@PathVariable("cakeId") Long cakeId) {
		return new Result(true, StatusCode.SUCCESS, "Cake Found!", cakeService.findById(cakeId));
	}

	@GetMapping(value = "/flavors/{flavorId}/cakes")
	public Result findAllByFlavorId(@PathVariable("flavorId") Long flavorId) {
		return new Result(true, StatusCode.SUCCESS, "Cakes Found Under Flavor " + flavorId,
				cakeService.findByFlavorId(flavorId));
	}

	// done
	@GetMapping(value = "/categories/{categoryId}/cakes")
	public Result findAllByCategoryId(@PathVariable("categoryId") Long categoryId) {
		return new Result(true, StatusCode.SUCCESS, "Cakes Found Under Category " + categoryId,
				cakeService.findByCategoryId(categoryId));
	}

	@GetMapping("/categories/{categoryId}/flavors/{flavorId}/cakes")
	public Result findAllByCategoryIdAndByFlavorId(@PathVariable("categoryId") Long categoryId,
			@PathVariable("flavorId") Long flavorId) {
		return new Result(true, StatusCode.SUCCESS, "Cake Found!",
				cakeService.findByCategoryIdAndByFlavorId(categoryId, flavorId));
	}

	// done
	@GetMapping(value = "/images/cakes_images/{cakeName}/{imageName}", produces = { MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE })
	public byte[] getImages(@PathVariable("cakeName") String cakeName, @PathVariable("imageName") String imageName)
			throws IOException {
		String s = System.getProperty("file.separator");
		return Files.readAllBytes(Paths.get(Constant.IMAGE_PATH + s + "cakes_images" + s + cakeName + s + imageName));

	}

}
