package com.nineplus.localhand.exceptions;

import com.nineplus.localhand.utils.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity maxUploadSizeExceeded(MaxUploadSizeExceededException ex, WebRequest request) {
		return new ResponseEntity<>(CommonConstants.MessageError.ER018, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
