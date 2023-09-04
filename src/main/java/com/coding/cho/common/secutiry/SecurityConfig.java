package com.coding.cho.common.secutiry;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	
	//url
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			//CSP를 설정하면 브라우저는 해당 정책에 따라 악의적인 스크립트의 실행을 방지
//			
//			.headers(header->header
//					.contentSecurityPolicy("default-src 'self'"))
//			
			.authorizeHttpRequests(authorize -> authorize
					.antMatchers("/webjars/**", "/js/**", "/css/**", "/images/**","/favicon/**").permitAll()
					.antMatchers("/","/signup","/common/**","/admin/**","/category/**","/index/**","/product/**","/faq/**").permitAll()
					.antMatchers("/cart/**").hasRole("USER")
					.anyRequest().authenticated()
			)
			.formLogin(form->form
					.loginPage("/signin") //get /signin
					//.loginProcessingUrl("/signin") //설정하지 않으면 loginPageUrl이 적용
					.usernameParameter("email")
					.passwordParameter("pass")
					.defaultSuccessUrl("/", true)//일단 테스트상황에서 무조건 index로 보내때 유용
					.permitAll()
			)
			.logout(logout->logout
					.logoutUrl("/signout")
					.logoutSuccessUrl("/")
			)
			;
		return http.build();
	}
	
	

}
