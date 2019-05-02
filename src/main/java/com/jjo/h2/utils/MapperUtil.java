package com.jjo.h2.utils;

import com.googlecode.jmapper.JMapper;

public interface MapperUtil {

  /**
   * Get the JMapper from a cached map
   * 
   * @param destination
   * @param source
   * @return
   */
  <D, S> JMapper<D, S> getMapper(Class<D> destination, Class<S> source);
}
