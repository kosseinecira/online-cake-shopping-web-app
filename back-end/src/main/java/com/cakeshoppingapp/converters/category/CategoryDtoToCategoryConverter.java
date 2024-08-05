package com.cakeshoppingapp.converters.category;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.dtoes.CategoryDTO;

@Component
public class CategoryDtoToCategoryConverter implements Converter<CategoryDTO, Category> {

	@Override
	public Category convert(CategoryDTO source) {
		return new Category(source.id(), source.name(), source.categoryImagePath());
	}

}
