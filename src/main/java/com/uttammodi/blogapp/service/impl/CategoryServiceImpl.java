package com.uttammodi.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uttammodi.blogapp.entity.Category;
import com.uttammodi.blogapp.exception.ResourceNotFoundException;
import com.uttammodi.blogapp.payload.CategoryDto;
import com.uttammodi.blogapp.repository.CategoryRepository;
import com.uttammodi.blogapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	/**
	 * @param categoryRepository
	 * @param modelMapper
	 */
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		
		Category savedCategpry  = categoryRepository.save(category);
		
		
		return modelMapper.map(savedCategpry, CategoryDto.class);
	}


	@Override
	public CategoryDto getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllcategories() {
		List<Category> allCategories = categoryRepository.findAll();
		
		return allCategories.stream().map((category)->modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryId);
		
		Category updatedCategory = categoryRepository.save(category);
		return modelMapper.map(updatedCategory, CategoryDto.class);	
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		categoryRepository.delete(category);
	}
	
	

}
