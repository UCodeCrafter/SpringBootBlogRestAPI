package com.uttammodi.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;

import com.uttammodi.blogapp.security.JwtAuthenticationEntryPoint;
import com.uttammodi.blogapp.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SecurityConfig {
	private UserDetailsService userDetailsService;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	

	/**
	 * @param userDetailsService
	 * @param jwtAuthenticationEntryPoint
	 * @param jwtAuthenticationFilter
	 */
	public SecurityConfig(UserDetailsService userDetailsService,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> 
//            authorize.anyRequest().authenticated()
            authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()		
            ).exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            ).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        PasswordEncoder encoder = passwordEncoder();
//
//        UserDetails uttam = User.builder()
//            .username("uttam")
//            .password(encoder.encode("uttam"))
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User.builder()
//            .username("admin")
//            .password(encoder.encode("admin"))
//            .roles("ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(uttam, admin);
//    }
}
