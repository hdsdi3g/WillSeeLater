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

import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "willseelater")
@Validated
public class Config {

	@NotNull
	private File workingDir;
	@NotNull
	private File cacheDir;
	private Map<String, StaticUser> users;
	private List<InternalUser> internalUsers;

	@PostConstruct
	public void init() {
		internalUsers = users.entrySet().stream()
		        .map(entry -> entry.getValue().makeInternalUser(entry.getKey()))
		        .collect(toUnmodifiableList());
	}

	@Validated
	public static class StaticUser {

		private String passwordHash;

		public InternalUser makeInternalUser(final String name) {
			return new InternalUser(name, this);
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(final String passwordHash) {
			this.passwordHash = passwordHash;
		}

	}

	public File getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(final File workingDir) {
		this.workingDir = workingDir;
	}

	public File getCacheDir() {
		return cacheDir;
	}

	public void setCacheDir(final File cacheDir) {
		this.cacheDir = cacheDir;
	}

	public void setUsers(final Map<String, StaticUser> users) {
		this.users = users;
	}

	public Map<String, StaticUser> getUsers() {
		return users;
	}

	public List<InternalUser> getInternalUsers() {
		return internalUsers;
	}

}
