package fu.rms.advice;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MessageError {

	private HttpStatus status;
	private List<String> message;

	public MessageError(HttpStatus status, List<String> message) {
		this.status = status;
		this.message = message;
	}

	public MessageError(HttpStatus status, String message) {
		this.status = status;
		this.message = Arrays.asList(message);
	}
}
