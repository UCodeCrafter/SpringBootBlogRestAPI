package com.uttammodi.blogapp.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uttammodi.blogapp.entity.Category;
import com.uttammodi.blogapp.entity.Post;
import com.uttammodi.blogapp.exception.ResourceNotFoundException;
import com.uttammodi.blogapp.payload.PostDto;
import com.uttammodi.blogapp.payload.PostResponse;
import com.uttammodi.blogapp.repository.CategoryRepository;
import com.uttammodi.blogapp.repository.PostRepository;
import com.uttammodi.blogapp.service.PostService;
@Service
public class PostServiceImpl implements PostService {
	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository
			categoryRepository) {
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}


	@Override
	public PostDto createPost(PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

		// Create DTO to Entity
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		
		//Convert entity to DTO
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
		
	}


	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		//Creating sort object for ascending and descending 
		Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		//Create pageable instance
		Pageable pageable =  PageRequest.of(pageNo, pageSize, sort);		
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		//Get content for page Object
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> content = listOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
		
		
	}
	



	@Override
	public PostDto getPostById(long id) {
		
		Post post =  postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}


	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get the post by id from the database
		
		Post post =  postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
		
	}


	@Override
	public void deletePostById(long id) {
		// get the post by id from the database
		Post post =  postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);		
				
	}
	
	//map to DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = mapper.map(post, PostDto.class);
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto;	
	}
	
	//map to Entity
	private Post mapToEntity(PostDto postDto){
		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
		
		
	}


	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		return posts.stream().map((post)->mapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}