package fu.rms.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fu.rms.exception.AddException;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, messageError, headers, messageError.getStatus(), request);
	}

	// handle missing parameter of request parameter
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());
	}

	// handle method not support
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		MessageError messageError = new MessageError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());
	}

	// handle media type
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

		MessageError messageError = new MessageError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(),
				builder.substring(0, builder.length() - 2));
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());
	}

	// default handle
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		MessageError apiError = new MessageError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// handle MethodArgumentTypeMismatch
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());
	}

	
	// handle not found exception
	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Username not found");
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle not found exception
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Not found entity");
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle add exception
	@ExceptionHandler({ AddException.class })
	public ResponseEntity<Object> handleAddException(AddException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "can't add entity");
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle update exception
	@ExceptionHandler({ UpdateException.class })
	public ResponseEntity<Object> handleUpdateException(UpdateException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "can't update entity");
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle delete exception
	@ExceptionHandler({ DeleteException.class })
	public ResponseEntity<Object> handleDeleteException(DeleteException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "can't delete entity");
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

}
