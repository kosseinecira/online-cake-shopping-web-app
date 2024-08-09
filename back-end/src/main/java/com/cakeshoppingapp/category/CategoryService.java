package com.cakeshoppingapp.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cakeshoppingapp.converters.category.CategoryDtoToCategoryConverter;
import com.cakeshoppingapp.converters.category.CategoryToCategoryDtoConverter;
import com.cakeshoppingapp.dtoes.CategoryDTO;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.cakeshoppingapp.utils.Constant;
import com.cakeshoppingapp.utils.FileUploadUtil;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryDtoToCategoryConverter categoryDtoToCategoryConverter;
	private final CategoryToCategoryDtoConverter categoryToCategoryDtoConverter;

	public CategoryService(CategoryRepository categoryRepository,
			CategoryDtoToCategoryConverter categoryDtoToCategoryConverter,
			CategoryToCategoryDtoConverter categoryToCategoryDtoConverter) {
		this.categoryRepository = categoryRepository;
		this.categoryDtoToCategoryConverter = categoryDtoToCategoryConverter;
		this.categoryToCategoryDtoConverter = categoryToCategoryDtoConverter;
	}

	public List<CategoryDTO> findAllCategories() {
		return categoryRepository.findAll().stream().map(c -> categoryToCategoryDtoConverter.convert(c)).toList();
	}

	public CategoryDTO saveCategory(CategoryDTO categoryDTO, MultipartFile categoryImage) {
		boolean categoryExist = categoryRepository.findByName(categoryDTO.name()).isPresent();
		if (categoryExist)
			throw new SomethingAlreadyExistException("Category With Name: " + categoryDTO.name());
		Category category = categoryDtoToCategoryConverter.convert(categoryDTO);
		if (categoryImage != null)
			if (!categoryImage.isEmpty()) {
				String link = saveImage(categoryImage);
				category.setImagePath(link);
			}
		Category savedCategory = categoryRepository.save(category);
		return categoryToCategoryDtoConverter.convert(savedCategory);
	}

	public CategoryDTO findById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Category With Id: " + id));
		return categoryToCategoryDtoConverter.convert(category);
	}

	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Category With Id: " + id));
		categoryRepository.delete(category);
	}

	public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO, MultipartFile categoryImage) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new SomethingNotFoundException("Category With Id: " + id));
		boolean categoryExist = categoryRepository.findByName(categoryDTO.name()).isPresent();
		if (categoryExist)
			throw new SomethingAlreadyExistException("Category With Name: " + categoryDTO.name());
		Category updatedCategoryDto = categoryDtoToCategoryConverter.convert(categoryDTO);
		String[] imagePath;
		if (categoryImage != null)
			if (!categoryImage.isEmpty()) {
				String link = saveImage(categoryImage);
				category.setImagePath(link);
			}
		Category updatedCategory = categoryRepository.save(updatedCategoryDto);
		return categoryToCategoryDtoConverter.convert(updatedCategory);
	}

	private String saveImage(MultipartFile categoryImage) {
		String s = System.getProperty("file.separator");
		String imageInfo[] = FileUploadUtil.saveImageToPath(categoryImage,
				Constant.IMAGE_PATH + s + "categories_images");
		System.out.println("IMAGE NAME :: " + imageInfo[0]);
		System.out.println("IMAGE PATH :: " + imageInfo[1]);
		// Note: The Slashes in the path is URL slashes not system slashes!!
		String result = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/images/categories_images/" + imageInfo[0]).toUriString();
		return result;
	}

}
