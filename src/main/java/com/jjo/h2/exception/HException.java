package com.jjo.h2.exception;

import lombok.Data;

@Data
public class HException {

	private int code;
	private String userMessage;
	private String techMessage;
}
