package com.cakeshoppingapp.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.dtoes.CategoryDTO;
import com.cakeshoppingapp.system.Result;
import com.cakeshoppingapp.system.StatusCode;

@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public Result getCategories() {
		return new Result(true, StatusCode.SUCCESS, "All Categories Found!", categoryService.findAllCategories());
	}

	@PostMapping(value = "/categories", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public Result addCategory(@RequestPart("categoryDto") CategoryDTO categoryDTO,
			@RequestPart(value = "categoryImage", required = false) MultipartFile categoryImage) {
		return new Result(true, StatusCode.CREATED, "Category Added Successfully!",
				categoryService.saveCategory(categoryDTO, categoryImage));
	}

	@GetMapping("/categories/{categoryId}")
	public CategoryDTO getCategoryById(@PathVariable("categoryId") Long id) {
		return categoryService.findById(id);
	}

	@DeleteMapping("/categories/{categoryId}")
	public Result deleteCategory(@PathVariable("categoryId") Long id) {
		categoryService.deleteCategory(id);
		return new Result(true, StatusCode.DELETED, "Category Deleted Successfully!");
	}

	@PutMapping(value = "/categories/{categoryId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public Result updateCategory(@PathVariable("categoryId") Long id,
			@RequestPart("categoryDto") CategoryDTO categoryDTO,
			@RequestPart(value = "categoryImage", required = false) MultipartFile categoryImage) {
		return new Result(true, StatusCode.UPDATED, "Category Added Successfully!",
				categoryService.updateCategory(id, categoryDTO, categoryImage));
	}

}
