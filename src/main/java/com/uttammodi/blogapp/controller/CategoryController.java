package com.uttammodi.blogapp.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.servlet.function.EntityResponse;

import com.uttammodi.blogapp.entity.Category;
import com.uttammodi.blogapp.payload.CategoryDto;
import com.uttammodi.blogapp.service.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private CategoryService categoryService;

	/**
	 * @param categoryService
	 */
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//Build add Category Rest API
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}
	//Build get Category REST API
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
		CategoryDto categoryDto = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);

	}
	//Build get all Categories REST PPI
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtos =  categoryService.getAllcategories();
		return ResponseEntity.ok(categoryDtos);
		
	}
	
	//Build Update Category REST API
	@PreAuthorize("hasRole('ADMIN')")
	@PutExchange("{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId){
		CategoryDto updatedCategoryDto =  categoryService.updateCategory(categoryDto, categoryId);
		
		return ResponseEntity.ok(updatedCategoryDto);
		
		
	}
	
	//Build a Delete REST API
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted sucessfully!");
	}
	
	
	
	
	

}
