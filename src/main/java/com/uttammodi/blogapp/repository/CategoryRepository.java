package com.uttammodi.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uttammodi.blogapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	

}
