package com.challenge.personalregister.config;

import com.challenge.personalregister.exception.BusinessException;
import com.challenge.personalregister.exception.ExceptionResolver;
import com.challenge.personalregister.util.Constants;
import javassist.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControlExceptionHandler {

	public static final String CONSTRAINT_VALIDATION_FAILED = "Constraint validation failed";

	@ExceptionHandler(value = { BusinessException.class})
	protected ResponseEntity<Object> handleConflict(BusinessException ex, WebRequest request) {
		HttpHeaders responseHeaders = new HttpHeaders();
		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Object> handleException(Throwable eThrowable) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(Optional.ofNullable(eThrowable.getMessage()).orElse(eThrowable.toString()))
				.description(ExceptionResolver.getRootException(eThrowable))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exMethod,
                                                                   WebRequest request) {

		String error = exMethod.getName() + " should be " + exMethod.getRequiredType().getName();

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(error)
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exMethod, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (ConstraintViolation<?> violation : exMethod.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(errors.toString())
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> validationError(MethodArgumentNotValidException exMethod) {

		BindingResult bindingResult = exMethod.getBindingResult();

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		List<String> fieldErrorDtos = fieldErrors.stream()
				.map(f -> f.getField().concat(":").concat(f.getDefaultMessage())).map(String::new)
				.collect(Collectors.toList());

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(fieldErrorDtos.toString())
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> validationError(HttpMessageNotReadableException exMethod) {

		Throwable mostSpecificCause = exMethod.getMostSpecificCause();
		String message = mostSpecificCause.getMessage();
		if (message == null) {
			message = exMethod.getMessage();
		}
		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(CONSTRAINT_VALIDATION_FAILED)
				.description(message)
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}
	
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<Object> handleException(MissingServletRequestParameterException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.BAD_REQUEST)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}
	
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleException(NotFoundException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.NOT_FOUND)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());

	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleException(EmptyResultDataAccessException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.NOT_FOUND)
				.message(Constants.NOT_FOUND)
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}
	
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException e) {

		BusinessException ex = BusinessException.builder()
				.httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
				.message(Optional.ofNullable(e.getMessage()).orElse(e.toString()))
				.description(ExceptionResolver.getRootException(e))
				.build();
		HttpHeaders responseHeaders = new HttpHeaders();

		return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
	}
}
