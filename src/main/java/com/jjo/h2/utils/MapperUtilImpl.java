package com.jjo.h2.utils;

import java.util.HashMap;
import java.util.Map;
import com.googlecode.jmapper.JMapper;
import lombok.Builder;
import lombok.Data;

public class MapperUtilImpl implements MapperUtil {

  private Map<MapperId, JMapper<?, ?>> mapperCache;

  public MapperUtilImpl() {
    super();
    mapperCache = new HashMap<>();
  }

  @SuppressWarnings("unchecked")
  public <D, S> JMapper<D, S> getMapper(Class<D> destination, Class<S> source) {
    MapperId mapperId = MapperId.builder().destination(destination).source(source).build();

    return (JMapper<D, S>) mapperCache.computeIfAbsent(mapperId,
        m -> new JMapper<>(destination, source));
  }
}


@Data
@Builder
final class MapperId {
  Class<?> destination;
  Class<?> source;
}
