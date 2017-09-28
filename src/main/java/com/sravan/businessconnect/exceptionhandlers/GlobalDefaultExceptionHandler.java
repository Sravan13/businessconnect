package com.sravan.businessconnect.exceptionhandlers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	  @ResponseStatus(value=HttpStatus.CONFLICT,reason="Data integrity violation")  
	  @ExceptionHandler(DataIntegrityViolationException.class)
	  public void conflict() {
	  }
	  
	  @ResponseStatus(value=HttpStatus.INSUFFICIENT_STORAGE,reason="SQLException or DataAccess exception")
	  @ExceptionHandler({SQLException.class,DataAccessException.class})
	  public void databaseError() {
	  }

	  @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR,reason="Exception at server")
	  @ExceptionHandler(Exception.class)
	  public void handleError(HttpServletRequest req, Exception ex) {
	  }

}
