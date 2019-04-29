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
    MapperId mapperId = new MapperId();
    mapperId.setDestination(destination);
    mapperId.setSource(source);

    return (JMapper<D, S>) mapperCache.computeIfAbsent(mapperId,
        m -> new JMapper<>(destination, source));
  }

  @Data
  final class MapperId {
    Class<?> destination;
    Class<?> source;
  }
}
