package fu.rms.exception;

public class AddException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AddException() {
		super();
	}
	
	public AddException(String message) {
		super(message);
	}
}
