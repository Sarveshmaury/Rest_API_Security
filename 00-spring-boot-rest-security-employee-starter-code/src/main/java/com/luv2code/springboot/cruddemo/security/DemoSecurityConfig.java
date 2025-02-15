package com.luv2code.springboot.cruddemo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {
	
	// add support for jdbc .. no more hard- coded user
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
		
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"select user_id,pw,active from members where user_id=?");
		
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select user_id , role from roles where user_id=?");
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(configurer ->
		             configurer
		             .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
		             .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
		             .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
		             .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
		             .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")
				
				);
		
		// USE HTTP BASIC AUTHENTICATION
		http.httpBasic(Customizer.withDefaults());
		
		// disable cross site request forgery (CSRF)
		// in general not required for stateless APIs that use post , put , delete , patch
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
		
		
	}
	
	/*
	@Bean
	public InMemoryUserDetailsManager userDetailsManager () {
		
		UserDetails Kamlesh =User.builder()
				.username("Kamlesh")
				.password("{noop}virat123")
				.roles("EMPLOYEE")
				.build();
		
		UserDetails Sarvesh =User.builder()
				.username("Sarvesh")
				.password("{noop}maurya123")
				.roles("EMPLOYEE" , "MANAGER")
				.build();
		
		
		UserDetails Akula =User.builder()
				.username("Akula")
				.password("{noop}teju123")
				.roles("EMPLOYEE" ,"MANAGER" , "ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(Kamlesh , Sarvesh , Akula);
		
		}
	*/

}
