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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import tv.hd3g.willseelater.service.UserService;

public class AppAuthProvider extends DaoAuthenticationProvider {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private UserService userDetailsService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final var auth = (UsernamePasswordAuthenticationToken) authentication;
		final var name = auth.getName();
		// ??? getUserDetailsService().loadUserByUsername(username)
		final var user = userDetailsService.loadUserByUsername(name);
		log.info("Credentials for {}: {}", name, auth.getCredentials());
		// throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
		// TODO check password
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
}
