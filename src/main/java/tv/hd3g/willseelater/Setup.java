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

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Configuration
public class Setup {

	@Bean
	public MessageSource messageSource() {
		return new ResourceBundleMessageSource();
	}

	@Bean
	public Cache getHttpClientCache(final Config config) {
		return new Cache(config.getCacheDir(), 10L * 1024 * 1024);
	}

	@Bean
	public OkHttpClient getOkHttpClient(final Cache cache) {
		return new OkHttpClient.Builder().cache(cache).build();
	}

}
