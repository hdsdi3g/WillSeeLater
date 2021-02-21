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
package tv.hd3g.willseelater.service;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tv.hd3g.willseelater.Config;

@Service
public class UserService implements UserDetailsService {
	private static final Logger log = LogManager.getLogger();

	@Autowired
	private Config config;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		Objects.requireNonNull(username);
		log.debug("Try to load user \"{}\"", username);

		return config.getInternalUsers().stream()
		        .filter(iU -> iU.getUsername()
		                .equalsIgnoreCase(username))
		        .findFirst()
		        .orElseThrow(() -> new UsernameNotFoundException("Can't found user: " + username));
	}
}
