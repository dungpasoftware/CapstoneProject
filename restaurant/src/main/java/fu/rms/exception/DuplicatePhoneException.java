package fu.rms.exception;

public class DuplicatePhoneException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DuplicatePhoneException() {
		super();
	}
	
	public DuplicatePhoneException(String message) {
		super(message);
	}
	
}
