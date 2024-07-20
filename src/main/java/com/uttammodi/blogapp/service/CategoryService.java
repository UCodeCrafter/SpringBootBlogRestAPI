package com.uttammodi.blogapp.service;


import java.util.List;

import com.uttammodi.blogapp.payload.CategoryDto;

public interface CategoryService {
	
	public CategoryDto addCategory(CategoryDto categoryDto);
	public CategoryDto getCategory(Long categoryId);
	public List<CategoryDto> getAllcategories();
	public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	public void deleteCategory(Long categoryId);

}
