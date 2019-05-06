package com.jjo.h2.exception;

public class HException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -343694406217060815L;

  /**
   * Same message for user and tech
   * @param message
   */
  public HException(String message) {
    super(message);
  }

  /**
   * Send message and Exception
   * @param message
   * @param e
   */
  public HException(String message, Exception e) {
    super(message, e);
  }
}
