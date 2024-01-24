package cl.tsoft.labs.ut.sample.rest.service.exception;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
        super(message);
    }
}
