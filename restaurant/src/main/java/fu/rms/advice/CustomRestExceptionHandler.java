package fu.rms.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fu.rms.exception.AddException;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	// handle valid data
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder message=new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			message.append(errorMessage);
			message.append("\n");
		});
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, message.toString());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());
	}

	// default handle
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		MessageError apiError = new MessageError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	// handle not found exception
	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {

		MessageError messageError = new MessageError(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle add exception
	@ExceptionHandler({ AddException.class })
	public ResponseEntity<Object> handleAddException(AddException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle update exception
	@ExceptionHandler({ UpdateException.class })
	public ResponseEntity<Object> handleUpdateException(UpdateException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

	// handle delete exception
	@ExceptionHandler({ DeleteException.class })
	public ResponseEntity<Object> handleDeleteException(DeleteException ex, WebRequest request) {
		MessageError messageError = new MessageError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<Object>(messageError, new HttpHeaders(), messageError.getStatus());

	}

}
