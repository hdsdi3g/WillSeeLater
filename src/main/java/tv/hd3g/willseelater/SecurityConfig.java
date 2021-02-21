/*
 * This file is part of willseelater.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * Copyright (C) hdsdi3g for hd3g.tv 2021
 *
 */
package tv.hd3g.willseelater;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import tv.hd3g.willseelater.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private UserService userDetailsService;
	// @Autowired
	// private AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf()
		        .disable()
		        .exceptionHandling()
		        .authenticationEntryPoint(new Http403ForbiddenEntryPoint() {})
		        .and()
		        .authenticationProvider(getProvider())
		        .formLogin()
		        .loginProcessingUrl("/login")
		        .successHandler(new AuthentificationLoginSuccessHandler())
		        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
		        .and()
		        .logout()
		        .logoutUrl("/logout")
		        .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
		        .invalidateHttpSession(true)
		        .and()
		        .authorizeRequests()
		        .antMatchers("/login").permitAll()
		        .antMatchers("/logout").permitAll()
		        .antMatchers("/home").authenticated()
		        .anyRequest().permitAll();
	}

	private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		@Override
		public void onAuthenticationSuccess(final HttpServletRequest request,
		                                    final HttpServletResponse response,
		                                    final Authentication authentication) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
		@Override
		public void onLogoutSuccess(final HttpServletRequest request,
		                            final HttpServletResponse response,
		                            final Authentication authentication) throws IOException, ServletException {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	@Bean
	public AuthenticationProvider getProvider() {
		final var provider = new AppAuthProvider();
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
