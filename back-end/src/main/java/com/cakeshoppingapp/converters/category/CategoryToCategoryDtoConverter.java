package com.cakeshoppingapp.converters.category;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.dtoes.CategoryDTO;

@Component
public class CategoryToCategoryDtoConverter implements Converter<Category, CategoryDTO> {

	@Override
	public CategoryDTO convert(Category source) {
		return new CategoryDTO(source.getId(), source.getName(), source.getImagePath());
	}

}
