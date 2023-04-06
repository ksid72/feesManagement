package com.fees.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new ProducingFilmUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/states/list_by_country/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER")
			.antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("ADMIN")
			.antMatchers("/studios/**", "/brands/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER")
			.antMatchers("/registration/**", "/brands/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER")
				.antMatchers("/saveControls/**", "/brands/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER")
			.antMatchers("/products/new", "/products/delete/**").hasAnyAuthority("ADMIN", "Editor")
			
			.antMatchers("/products/edit/**", "/products/save", "/products/check_unique")
				.hasAnyAuthority("ADMIN", "Editor", "STUDIO_MASTER")
				
			.antMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
				.hasAnyAuthority("ADMIN", "Editor", "STUDIO_MASTER", "Shipper")
				
			.antMatchers("/products/**").hasAnyAuthority("ADMIN", "Editor")
			
			.antMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER", "Shipper")
			
			.antMatchers("/products/detail/**", "/customers/detail/**").hasAnyAuthority("ADMIN", "Editor", "STUDIO_MASTER", "Assistant")

			.antMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**").hasAnyAuthority("ADMIN", "STUDIO_MASTER")
			
			.antMatchers("/orders_shipper/update/**").hasAuthority("Shipper")
			
			.antMatchers("/reviews/**").hasAnyAuthority("ADMIN", "Assistant")
			
			.anyRequest().authenticated()
			.and()
			.formLogin()			
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll()
			.and()
				.rememberMe()
					.key("AbcDefgHijKlmnOpqrs_1234567890")
					.tokenValiditySeconds(7 * 24 * 60 * 60);
					;
			http.headers().frameOptions().sameOrigin();
		    http.headers()
				// do not use any default headers unless explicitly listed
				.defaultsDisabled()
				.cacheControl();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}

	
}
