package com.jjo.h2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jjo.h2.utils.MapperUtil;
import com.jjo.h2.utils.MapperUtilImpl;

@Component
public class MapperConfiguration {

	@Bean
	public MapperUtil initapper() {
		return new MapperUtilImpl();
	}
}
