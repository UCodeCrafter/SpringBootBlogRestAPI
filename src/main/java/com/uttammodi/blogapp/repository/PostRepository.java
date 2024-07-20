package com.uttammodi.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.uttammodi.blogapp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByCategoryId(Long categoryId);
	
}
