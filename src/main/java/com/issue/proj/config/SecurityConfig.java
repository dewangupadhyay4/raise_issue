package com.issue.proj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatchers;

@Configuration
public class SecurityConfig {

	@Bean	
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		.csrf()
		.disable()
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/api/v1/send-otp",
						"/v3/api-docs/**",
						"/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/error"
						).permitAll()
				.anyRequest().permitAll()
				)
		.httpBasic(Customizer.withDefaults());
		return httpSecurity.build();
	}
	
}
