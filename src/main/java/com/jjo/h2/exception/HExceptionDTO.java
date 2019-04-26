package com.jjo.h2.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HExceptionDTO {

	private Long id;
	private int code;
	private String userMessage;
	private String techMessage;
	private LocalDateTime eventTime;
}
