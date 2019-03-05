package com.jjo.h2.utils;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.jmapper.JMapper;

import lombok.Data;

public class MapperUtilImpl implements MapperUtil {

	private Map<MapperId, JMapper<?, ?>> mapperCache;

	public MapperUtilImpl() {
		super();
		mapperCache = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public <D, S> JMapper<D, S> getMapper(Class<D> destination, Class<S> source) {
		JMapper<D, S> mapper = null;

		MapperId mapperId = new MapperId();
		mapperId.setDestination(destination);
		mapperId.setSource(source);

		if (!mapperCache.containsKey(mapperId)) {
			mapper = new JMapper<>(destination, source);
			mapperCache.put(mapperId, mapper);
		} else {
			mapper = (JMapper<D, S>) mapperCache.get(mapperId);
		}

		return mapper;
	}

	@Data
	final class MapperId {
		Class<?> destination;
		Class<?> source;
	}
}