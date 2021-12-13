package com.betatech.padaria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.betatech.padaria.filter.CustomAuthenticationFilter;
import com.betatech.padaria.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

	@Configuration
	@EnableWebSecurity
	@RequiredArgsConstructor
	public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
		private final UserDetailsService userDetailsService;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
			System.out.println(auth);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
			customAuthenticationFilter.setFilterProcessesUrl("/padaria/login");
			http.csrf().disable();
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.authorizeRequests().antMatchers("/padaria/login/**","/padaria/token/refresh/**").permitAll();
			http.authorizeRequests().antMatchers(HttpMethod.GET,"/padaria/funcionario/**");
			http.authorizeRequests().antMatchers(HttpMethod.POST,"/padaria/produto/salvar/**","/padaria/funcionario/salvar");
			http.authorizeRequests().anyRequest().authenticated();
			http.addFilter(customAuthenticationFilter);
			http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception{
			return super.authenticationManagerBean();
		}
}