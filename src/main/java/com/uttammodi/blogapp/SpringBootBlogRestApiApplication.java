package com.uttammodi.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.support.Repositories;

import com.uttammodi.blogapp.entity.Role;
import com.uttammodi.blogapp.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
			title="Spring boot blog App REST APIs",
			description = "Spring Boot Blog app REST APIs Documentation",
			version = "v1.0",
			contact = @Contact(
					name = "Uttam ",
					email = "uttam3179@gmail.com",
					url = "www.uttammodi.com"
					),
			license = @License(
					name = "Apache 2.0",
					url = "www.uttammodi.com/license"
					)
		),
		externalDocs = @ExternalDocumentation(
				description = "Sprig boot Blog app documentation",
				url="www.uttammodi.com/spring-boot-blog-app-documentation"
				)
		)
public class SpringBootBlogRestApiApplication implements CommandLineRunner{
	
  
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogRestApiApplication.class, args);
	}
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		
		roleRepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
		
		
		
	}

}
